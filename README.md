# Workout App Kotlin

Workout App Kotlin is a mobile application built with Kotlin that allows users to explore and track different workout exercises. The app provides a user-friendly interface for browsing exercise details, including body parts targeted, equipment required, and animated GIFs demonstrating the exercises.

## Features

- Browse a wide range of workout exercises.
- View exercise details, including name, body parts targeted, equipment required, and animated GIFs.
- Track your workout progress and mark completed exercises.
- Search exercises by name or body part.
- Customize your workout routine and create personalized exercise lists.
- User-friendly interface with smooth navigation and intuitive design.

## Screenshots


https://github.com/maorshriki/workout_app_kotlin/assets/74913575/f2f5f706-a87a-4725-9616-f7f99d6dd5d1


## Technologies Used

- Kotlin - The primary programming language for developing the Android application.
- Android Jetpack - A suite of libraries and tools providing components and architectural guidance for building robust Android apps.
  - ViewModel - Manages and retains UI-related data across configuration changes.
  - LiveData - Observable data holder that notifies views of any changes.
  - Room - Provides an abstraction layer over SQLite for local data persistence.
  - RecyclerView - Displays large sets of data efficiently in a scrollable list.
- OkHttp - A powerful HTTP client for making network requests.
- Glide - An image loading and caching library for displaying GIF animations and images.
- JSON - Parsing and handling JSON data received from the API.
- RapidAPI - Integration with RapidAPI platform for accessing workout exercise data.

## Installation

1. Clone the repository: `git clone https://github.com/your-username/workout_app_kotlin.git`
2. Open the project in Android Studio.
3. Build and run the application on an Android emulator or physical device.

## API Integration

The Workout App Kotlin utilizes the WgerApiClient to fetch workout exercise data from the RapidAPI platform. To successfully run the app, make sure you have obtained a valid RapidAPI key and host. Update the `WgerApiClient` class with your API key and host in the `getExerciseList` function.

```kotlin
private val rapidApiKey = "YOUR_API_KEY"
private val rapidApiHost = "YOUR_API_HOST"
```

## Contributions

Contributions to the Workout App Kotlin are welcome! If you find any bugs or want to enhance the app with new features, feel free to open issues and submit pull requests.

Before contributing, please review the [Contributing Guidelines](CONTRIBUTING.md) for more information.

---

We hope you enjoy using the Workout App Kotlin! If you have any questions or need assistance, please contact us
