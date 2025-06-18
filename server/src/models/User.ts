import mongoose, { Document, Schema } from 'mongoose';

export interface IUser extends Document {
  googleId: string;
  name: string;
  email: string;
  avatarUrl?: string;
  groups: mongoose.Types.ObjectId[];
  preferences: string[]; // genre ids
}

const UserSchema = new Schema<IUser>({
  googleId: { type: String, required: true, unique: true },
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  avatarUrl: { type: String },
  groups: [{ type: Schema.Types.ObjectId, ref: 'Group' }],
  preferences: [{ type: String }],
});

export default mongoose.model<IUser>('User', UserSchema);
