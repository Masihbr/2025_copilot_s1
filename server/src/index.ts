import express from 'express';
import cors from 'cors';
import mongoose from 'mongoose';
import dotenv from 'dotenv';
import authRoutes from './routes/auth';
import preferencesRoutes from './routes/preferences';
import votingRoutes from './routes/voting';
import genresRoutes from './routes/genres';
import notificationsRoutes from './routes/notifications';

dotenv.config();

const app = express();
app.use(cors());
app.use(express.json());

const PORT = process.env.PORT || 4000;
const MONGODB_URI = process.env.MONGODB_URI || '';

mongoose.connect(MONGODB_URI)
  .then(() => console.log('MongoDB connected'))
  .catch((err) => console.error('MongoDB connection error:', err));

app.get('/', (req, res) => {
  res.send('MovieSwipe backend is running!');
});

app.use('/auth', authRoutes);
app.use('/preferences', preferencesRoutes);
app.use('/voting', votingRoutes);
app.use('/genres', genresRoutes);
app.use('/notifications', notificationsRoutes);

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
