import express from 'express';
import { authenticateJWT, AuthRequest } from '../middleware/auth';
import User from '../models/User';

const router = express.Router();

// GET /preferences - get current user's preferences
router.get('/', authenticateJWT, async (req: AuthRequest, res) => {
  const user = await User.findById(req.userId);
  if (!user) return res.status(404).json({ error: 'User not found' });
  res.json({ preferences: user.preferences });
});

// PUT /preferences - update current user's preferences
router.put('/', authenticateJWT, async (req: AuthRequest, res) => {
  const { preferences } = req.body;
  const user = await User.findByIdAndUpdate(
    req.userId,
    { preferences },
    { new: true }
  );
  if (!user) return res.status(404).json({ error: 'User not found' });
  res.json({ preferences: user.preferences });
});

export default router;
