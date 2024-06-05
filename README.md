# nimble-challenge

Challenge from Nimble for Android developer position
Survey App

## Features

- **Splash**:  Show splash screen and checked if user login or not.
- **Login**:  Users can login via Email & Password.
- **Forgot Password**:  Users can request to reset password.
- **Home**:  User can Browse a list of surveys ( **Offline support with pagination** ).
- **Survey Details**: Temp survey detail with back button and receive Id from Home survey list.

## Getting started

- Clone this repository.
- Modify `local.properties` file in the project's `root` folder with these information:

~~~
CLIENT_ID_DEV="[client_id_dev]"
CLIENT_SECRET_DEV="[client_secret_dev]"

CLIENT_ID="[client_id_prod]"
CLIENT_SECRET="[client_secrete_prod]"
~~~

- Build and run.

## Techs in App

- **Language**: Kotlin
- **Architecture**: MVVM with Clean Architecture
- **UI**: Jetpack Compose with Material 3
- **Paging**: Paging 3 with RemoteMediator
- **Networking**: Retrofit
- **Database**: Room
- **Dependency Injection**: Hilt
- **Image Loading**: Coil
- **Preferences**: EncryptedSharedPreferences
- **Code style conventions**: KtLint
- **Testing**: JUnit, MockK and Turbine
