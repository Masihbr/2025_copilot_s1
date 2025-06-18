import express from 'express';
import jwt from 'jsonwebtoken';
import User from '../models/User';
import axios from 'axios';

const router = express.Router();

// POST /auth/google
router.post('/google', async (req, res) => {
  const { idToken } = req.body;
  try {
    // Verify Google ID token
    const googleRes = await axios.get(
      `https://oauth2.googleapis.com/tokeninfo?id_token=${idToken}`
    );
    const { sub: googleId, name, email, picture } = googleRes.data;
    let user = await User.findOne({ googleId });
    if (!user) {
      user = await User.create({
        googleId,
        name,
        email,
        avatarUrl: picture,
        groups: [],
        preferences: [],
      });
    }
    const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET as string, {
      expiresIn: '7d',
    });
    res.json({ token, user });
  } catch (err) {
    res.status(401).json({ error: 'Invalid Google token' });
  }
});

export default router;
