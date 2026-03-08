Product Listing App

This Android application displays a list of products fetched from an API. 
Users can search, filter by category, view product details, and mark products as favorites.
The app is built using Jetpack Compose and MVVM architecture.


Architecture

The project follows the MVVM (Model–View–ViewModel) architecture.

UI Layer – Compose screens such as ProductList, ProductDetail, and FavoriteScreen.
ViewModel – Manages UI state, pagination, search,filtering, and favorites.
Repository – Handles communication with API and local database.
Data Layer – Uses Retrofit for network calls and Room for storing favorite products.


Setup Instructions

1.Clone the repository
    ```git clone https://github.com/Kavita-Bharti/Android_Assignment.git```

2.Open the project in Android Studio

   File → Open → Select the project folder.

3.Sync Gradle

  Allow Android Studio to download required dependencies.

4.Run the app

Connect an Android device or start an emulator and run the project.

Minimum SDK: API 24
