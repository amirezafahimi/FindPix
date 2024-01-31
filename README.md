# Image Search App

## Overview

This Android application is an image search app that allows users to explore and discover images based on keywords. The app utilizes the Pixabay public web services API to fetch and display image results. It is built using modern Android development practices, including Clean Architecture, Jetpack Compose, Coroutines, Hilt, Coil, Retrofit, and follows best practices for organized, efficient, and readable code.

## Tech Stack

- **Clean Architecture**: Emphasizes separation of concerns, making the codebase modular, and promoting testability.
- **Jetpack Compose**: Enables a modern and declarative UI development approach, making the app's UI flexible and efficient.
- **Coroutines**: Manages asynchronous tasks, ensuring smooth and responsive user interactions.
- **Hilt**: Handles dependency injection, promoting code scalability and maintainability.
- **Coil**: Facilitates efficient image loading and displaying.
- **Retrofit**: Performs network requests to the Pixabay API for fetching image data.
- **Room**: Manages local caching of search results, ensuring offline accessibility.

## Project Structure

The project is organized according to Clean Architecture principles:

- **Data Layer**: Handles data retrieval from the Pixabay API and local caching using Room.
- **Domain Layer**: Contains business logic and use cases for interacting with the data.
- **Presentation Layer**: Comprises the UI logic implemented with Jetpack Compose.

## Features

1. **Search Functionality**: Users can enter keywords and search for images.
2. **Pixabay API Integration**: Fetches and parses image data from the Pixabay API.
3. **Results List**: Displays a list of image results with thumbnails, usernames, and tags.
4. **Offline Caching**: Utilizes Room for local caching, providing offline access to previous search results.
5. **Detailed View**: Allows users to view more details about an image, including a larger version, user's name, tags, likes, downloads, and comments.
6. **Default Search on Startup**: Initiates a search for the string "fruits" when the app launches for first time.
7. **Configuration Changes Handling**: Gracefully handles configuration changes to preserve app state.

## Initial Search Query

When the user opens the app for the first time, the default search query is set to "fruits." This provides users with a predefined starting point to explore images related to fruits.
However, after the initial launch, the app will remember the user's last search query. So, subsequent times the app is opened, it will automatically initiate a search with the last query used by the user.
If the app has not been used before or if the user cleared their search history, it will default to the "fruits" search query again.

## Testing

The project includes some comprehensive unit tests and instrumented tests to ensure code correctness and reliability.
