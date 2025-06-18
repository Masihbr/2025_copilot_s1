# MovieSwipe Backend (Node.js/TypeScript)

## Prerequisites
- Node.js 18+
- npm or yarn
- MongoDB (local or Azure Cosmos DB for MongoDB API)

## Setup
1. Clone the repository.
2. Install dependencies: `npm install`
3. Create a `.env` file with the following:
   - `MONGODB_URI=your_mongodb_connection_string`
   - `TMDB_API_KEY=your_tmdb_api_key`
   - `JWT_SECRET=your_jwt_secret`
4. Start the server: `npm run dev`

## Azure Deployment
1. Create a Resource Group, App Service, and Cosmos DB (MongoDB API) in Azure.
2. Set environment variables in App Service for MongoDB, TMDb, and JWT.
3. Deploy using GitHub Actions, Azure CLI, or VS Code Azure extension.

## Features
- REST API for group, user, and voting management
- TMDb API integration for movie data
- JWT authentication
- Push notification endpoint for FCM

---
See the main README for client setup and Google/Firebase instructions.
