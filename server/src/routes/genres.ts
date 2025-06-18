import express from 'express';
import axios from 'axios';

const router = express.Router();

// GET /genres - get list of movie genres from TMDb
router.get('/', async (req, res) => {
  try {
    const tmdbRes = await axios.get('https://api.themoviedb.org/3/genre/movie/list', {
      params: { api_key: process.env.TMDB_API_KEY },
    });
    res.json(tmdbRes.data.genres);
  } catch (err) {
    res.status(500).json({ error: 'Failed to fetch genres' });
  }
});

export default router;
