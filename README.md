# MovieSwipe

MovieSwipe is a group movie recommendation and voting app. It consists of a native Android client (Kotlin, Jetpack Compose) and a Node.js/TypeScript backend running on Azure with MongoDB and TMDb API integration.

## Project Structure

- `/client` — Android app (Kotlin, Jetpack Compose)
- `/server` — Node.js backend (TypeScript, MongoDB)

## Setup Instructions

### 1. Android App (Client)
- Written in Kotlin using Jetpack Compose.
- Uses Google Sign-In for authentication.
- Uses Firebase Cloud Messaging for push notifications.

#### Google Authentication Setup
1. Go to [Google Cloud Console](https://console.cloud.google.com/).
2. Create a new project (or select an existing one).
3. Navigate to **APIs & Services > Credentials**.
4. Click **Create Credentials > OAuth client ID**.
5. Select **Android** and provide your app's package name and SHA-1 certificate fingerprint.
6. Download the `google-services.json` file and place it in `/client/app`.

#### Firebase Cloud Messaging Setup
1. Go to [Firebase Console](https://console.firebase.google.com/).
2. Add a new project (or use the same as above).
3. Register your Android app and download the `google-services.json` file (if not already done).
4. Enable Cloud Messaging in the Firebase console.

### 2. Backend (Server)
- Written in TypeScript (Node.js)
- Uses MongoDB (self-hosted or Azure Cosmos DB for MongoDB API)
- Integrates with TMDb API for movie data

#### Azure Setup
1. Create an [Azure account](https://azure.microsoft.com/).
2. Create a **Resource Group**.
3. Create an **App Service** for Node.js.
4. Create an **Azure Cosmos DB** instance (MongoDB API).
5. Set environment variables for MongoDB connection and TMDb API key.

#### TMDb API Setup
1. Go to [TMDb API](https://www.themoviedb.org/documentation/api).
2. Create an account and request an API key.
3. Add the API key to your backend environment variables.

---

## Getting Started

- See `/client/README.md` and `/server/README.md` for detailed setup and run instructions for each part.
