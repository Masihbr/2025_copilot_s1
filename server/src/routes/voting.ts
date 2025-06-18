import express from 'express';
import { authenticateJWT, AuthRequest } from '../middleware/auth';
import VotingSession from '../models/VotingSession';
import Group from '../models/Group';
import User from '../models/User';
import axios from 'axios';
import mongoose from 'mongoose';

const router = express.Router();

// POST /voting/start - start a voting session (owner only)
router.post('/start', authenticateJWT, async (req: AuthRequest, res) => {
  const { groupId } = req.body;
  const group = await Group.findById(groupId).populate('members');
  if (!group) return res.status(404).json({ error: 'Group not found' });
  if (group.owner.toString() !== req.userId) return res.status(403).json({ error: 'Not owner' });

  // Get all members' preferences
  await group.populate('members');
  const members = group.members as any[];
  const allPreferences = members.map((m) => m.preferences || []).flat();
  const genreCounts: Record<string, number> = {};
  allPreferences.forEach((g) => { genreCounts[g] = (genreCounts[g] || 0) + 1; });
  // Pick top genres
  const topGenres = Object.entries(genreCounts)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 3)
    .map(([g]) => g);

  // Fetch movies from TMDb
  const tmdbRes = await axios.get('https://api.themoviedb.org/3/discover/movie', {
    params: {
      api_key: process.env.TMDB_API_KEY,
      with_genres: topGenres.join(','),
      sort_by: 'popularity.desc',
      page: 1,
    },
  });
  const movies = tmdbRes.data.results.map((m: any) => m.id.toString());

  // Create voting session
  const session = await VotingSession.create({
    group: group._id,
    movies,
    votes: [],
    startedAt: new Date(),
  });
  group.activeSession = session._id as mongoose.Types.ObjectId;
  await group.save();
  res.json(session);
});

// POST /voting/vote - vote for a movie
router.post('/vote', authenticateJWT, async (req: AuthRequest, res) => {
  const { sessionId, movieId, vote } = req.body;
  const session = await VotingSession.findById(sessionId);
  if (!session) return res.status(404).json({ error: 'Session not found' });
  // Remove previous vote by this user for this movie
  session.votes = session.votes.filter(
    (v) => !(v.user.toString() === req.userId && v.movie === movieId)
  );
  const userObjectId = new mongoose.Types.ObjectId(req.userId);
  session.votes.push({ user: userObjectId, movie: movieId, vote });
  await session.save();
  res.json({ success: true });
});

// POST /voting/end - end voting session (owner only)
router.post('/end', authenticateJWT, async (req: AuthRequest, res) => {
  const { groupId } = req.body;
  const group = await Group.findById(groupId);
  if (!group) return res.status(404).json({ error: 'Group not found' });
  if (group.owner.toString() !== req.userId) return res.status(403).json({ error: 'Not owner' });
  const session = await VotingSession.findById(group.activeSession);
  if (!session) return res.status(404).json({ error: 'Session not found' });
  session.endedAt = new Date();
  await session.save();
  group.activeSession = null;
  await group.save();

  // Compute winning movie
  const movieVotes: Record<string, number> = {};
  session.votes.forEach((v) => {
    if (v.vote) movieVotes[v.movie] = (movieVotes[v.movie] || 0) + 1;
  });
  const [winner] = Object.entries(movieVotes).sort((a, b) => b[1] - a[1])[0] || [];

  // Fetch movie details from TMDb
  let movieDetails = null;
  if (winner) {
    const tmdbRes = await axios.get(`https://api.themoviedb.org/3/movie/${winner}`, {
      params: { api_key: process.env.TMDB_API_KEY },
    });
    movieDetails = tmdbRes.data;
  }
  res.json({ winner, movieDetails });
});

export default router;
