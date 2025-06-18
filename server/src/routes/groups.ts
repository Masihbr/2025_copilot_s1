import express from 'express';
import { authenticateJWT, AuthRequest } from '../middleware/auth';
import Group from '../models/Group';
import User from '../models/User';
import { nanoid } from 'nanoid';
import mongoose from 'mongoose';

const router = express.Router();

// POST /groups - create group
router.post('/', authenticateJWT, async (req: AuthRequest, res) => {
  const { name } = req.body;
  const owner = req.userId;
  const inviteCode = nanoid(8);
  const group = await Group.create({ name, owner, members: [owner], inviteCode });
  await User.findByIdAndUpdate(owner, { $push: { groups: group._id } });
  res.json(group);
});

// POST /groups/join - join group by invite code
router.post('/join', authenticateJWT, async (req: AuthRequest, res) => {
  const { inviteCode } = req.body;
  const userId = req.userId;
  const group = await Group.findOne({ inviteCode });
  if (!group) return res.status(404).json({ error: 'Group not found' });
  const userObjectId = new mongoose.Types.ObjectId(userId);
  if (!group.members.some((m) => m.equals(userObjectId))) {
    group.members.push(userObjectId);
    await group.save();
    await User.findByIdAndUpdate(userId, { $push: { groups: group._id } });
  }
  res.json(group);
});

// DELETE /groups/:id - delete group (owner only)
router.delete('/:id', authenticateJWT, async (req: AuthRequest, res) => {
  const group = await Group.findById(req.params.id);
  if (!group) return res.status(404).json({ error: 'Group not found' });
  if (group.owner.toString() !== req.userId) return res.status(403).json({ error: 'Not owner' });
  await group.deleteOne();
  res.json({ success: true });
});

export default router;
