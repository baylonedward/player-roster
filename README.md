# player-roster
A demo android app to manage teams, its players and training schedules.
Created using Kotlin.

## Architecture: MVVM

The choice was made as this is the recommended pattern from Google's jetpack architecture components.

1. Model - represents the data layer of application which contains source of data.
2. View - represents the UI layer and listens for state changes from the ViewModel.
3. ViewModel - mediates between the View and Model, and mostly contains the business logic.

Resource: [[Guide to app architecture | Android Developers](https://developer.android.com/jetpack/guide)]

### Dependency Injection

Used Android's Hilt to automated dependency Injection

Resource: [[Dependency injection with Hilt | Android Developers](https://developer.android.com/training/dependency-injection/hilt-android)]

### UI and Navigation

Used the Single Activity architecture and Navigation Component
from Jetpack's architecture component to implement screen navigations.

I also implemented a Coordinator like pattern for navigation to centralize management of screen navigations, all fragment screen options are declared and handled by an activity-wide view model, changes are observe and executed by the activity.

Resource: [Navigation | Android Developers](https://developer.android.com/guide/navigation)

### Programming

Implemented the observer pattern using kotlin coroutines' Flow and Android LiveData.
Implemented state pattern.

Used kotlin's suspending function feature for easier implementation
of coroutines for threading and asynchronous task.

Resource: [Kotlin flows on Android | Android Developers](https://developer.android.com/kotlin/flow)

## Persistence

Used Android Room to have easier interface to Android's SQLite for local data storage.

Resource: [Save data in a local database using Room | Android Developers](https://developer.android.com/training/data-storage/room)