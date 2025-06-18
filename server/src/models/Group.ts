import mongoose, { Document, Schema } from 'mongoose';

export interface IGroup extends Document {
  name: string;
  owner: mongoose.Types.ObjectId;
  members: mongoose.Types.ObjectId[];
  inviteCode: string;
  activeSession: mongoose.Types.ObjectId | null;
}

const GroupSchema = new Schema<IGroup>({
  name: { type: String, required: true },
  owner: { type: Schema.Types.ObjectId, ref: 'User', required: true },
  members: [{ type: Schema.Types.ObjectId, ref: 'User' }],
  inviteCode: { type: String, required: true, unique: true },
  activeSession: { type: Schema.Types.ObjectId, ref: 'VotingSession', default: null },
});

export default mongoose.model<IGroup>('Group', GroupSchema);
