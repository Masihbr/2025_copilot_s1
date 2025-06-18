import express from 'express';
import { authenticateJWT, AuthRequest } from '../middleware/auth';
import axios from 'axios';

const router = express.Router();

// POST /notifications/send - send push notification to a device
router.post('/send', authenticateJWT, async (req: AuthRequest, res) => {
  const { fcmToken, title, body } = req.body;
  try {
    const response = await axios.post(
      'https://fcm.googleapis.com/fcm/send',
      {
        to: fcmToken,
        notification: { title, body },
      },
      {
        headers: {
          Authorization: `key=${process.env.FCM_SERVER_KEY}`,
          'Content-Type': 'application/json',
        },
      }
    );
    res.json({ success: true, response: response.data });
  } catch (err) {
    res.status(500).json({ error: 'Failed to send notification' });
  }
});

export default router;
