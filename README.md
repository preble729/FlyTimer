# FlyTimer

FlyTimer is an Android app for recording flying sprint times, organizing athletes and races, and reviewing recent performance data in one place.

## What It Currently Does

- Records timed sprint efforts with a start/stop timer.
- Requires both an athlete and a race selection before a time can be started/exported.
- Saves recorded times locally after export.
- Lets you reset the timer between reps.
- Manages athletes from Settings.
- Manages races from Settings.
- Shows a dashboard with:
  - athlete count
  - race count
  - total recorded times
  - best recorded time
  - recent race results
- Lists all athletes on a dedicated Athletes screen.
- Opens an athlete detail page with:
  - best times by race
  - previous race history in chronological order
- Uses local on-device persistence with Room.
- Uses Hilt for dependency injection.
- Uses Jetpack Compose for the UI.

## Project Structure

- `app` - Android UI, navigation, and screen-level view models
- `domain` - models and repository interfaces
- `data` - Room database, DAOs, and repository implementations
- `di` - Hilt modules that wire data and domain together

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Hilt
- Room
- Coroutines and Flow

## Requirements

- Android Studio
- JDK 17
- Android SDK 35
- Minimum Android version: API 26

## Running The Project

1. Open the project in Android Studio.
2. Let Gradle sync.
3. Run the `app` configuration on an emulator or Android device.

## Notes

- The app currently stores data locally on the device.
- The timer exports a selected athlete/race result into local storage.
- There is team-related code in the project, but the main app navigation and active dependency wiring are currently focused on athletes, races, times, and dashboard data.

## Upcoming Features

- Add ideas here
