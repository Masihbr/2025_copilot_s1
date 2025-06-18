import mongoose, { Document, Schema } from 'mongoose';

export interface IVotingSession extends Document {
  group: mongoose.Types.ObjectId;
  movies: string[]; // TMDb movie IDs
  votes: {
    user: mongoose.Types.ObjectId;
    movie: string;
    vote: boolean;
  }[];
  startedAt: Date;
  endedAt?: Date;
}

const VotingSessionSchema = new Schema<IVotingSession>({
  group: { type: Schema.Types.ObjectId, ref: 'Group', required: true },
  movies: [{ type: String, required: true }],
  votes: [
    {
      user: { type: Schema.Types.ObjectId, ref: 'User', required: true },
      movie: { type: String, required: true },
      vote: { type: Boolean, required: true },
    },
  ],
  startedAt: { type: Date, default: Date.now },
  endedAt: { type: Date },
});

export default mongoose.model<IVotingSession>('VotingSession', VotingSessionSchema);
