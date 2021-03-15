# Bitlue

<img src="https://j.gifs.com/0Y41GG.gif" width=40% height=40%> <img src="https://j.gifs.com/E8DAnK.gif" width=40% height=40%>

> GIF Source: [Bitlue_DemoVideo](Bitlue_DemoVideo.mp4)

Bitlue is an app where you can check the Bitcoin's current market price value (Bitcoin + value = Bitlue) and its records.

The price values were requested from [Blockchain API](https://www.blockchain.com/charts).

## Getting Started

In the last 3 days, I developed this nice app using the following tech stack:
- Kotlin
- MVVM architecture
- Jetpack Architecture Components: ViewModel, Navigation...
- Hilt - Dependency injection
- Coroutines + Flow
- Material Design Components
- Retrofit
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)

## MVVM Architecture

In this section I will explain the MVVM architecture followed and all its different parts:

### UI
First of all, the UI is strongly supported by a solid design system supported by the **Material Design**.
Thanks to that, create a consistent design and a **dark theme** in parallel were able to achieve.

This project follows the *Single-Activity pattern*, where **`MainActivity.kt`** its the base Activity. 
From it we can differentiate 2 Fragments:
  * **`BitcoinValueFragment.kt`**: you can check the current price value of the Bitcoin and a chart of its records.
  
<img src="https://github.com/Trosydman/Bitlue/blob/main/Bitlue_LightTheme_HomeScreen.png" width=40% height=40%> <img src="https://github.com/Trosydman/Bitlue/blob/main/Bitlue_DarkTheme_HomeScreen.png" width=40% height=40%>
  
  * **`FilterSettingsDialogFragment.kt`**: filters the chart values between 2 filters: time 
  range(`enum class FilterTimeRange`) and rolling average(`enum class FilterRollingAverage`).
  
  <img src="https://github.com/Trosydman/Bitlue/blob/main/Bitlue_LightTheme_FilterSettings.png" width=40% height=40%> <img src="https://github.com/Trosydman/Bitlue/blob/main/Bitlue_DarkTheme_FilterSettings.png" width=40% height=40%>

Both Fragments are consuming the same ViewModel: **`MainViewModel.kt`**. The `FilterSettingsDialogFragment` was highly dependent on the `BitcoinValueFragment` and vice-versa, so I thought it make sense to share the same *ViewModel*.

Apart from the use of `LiveData` to display some reactive data, the other part of the communication between the Fragments and the ViewModel is made by the **unidirectional event-state data flow pattern**. This pattern allows to separate the UI events (of Fragments) from the UI states (requested by the `MainViewModel`). It highly achieves to decouple the UI logic and the business logic and it organizes them in a better way.

The chart in the `BitcoinValueFragment` is powered by [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart). In addition, a custom view called **`BitcoinValueChart`** was created to wrap all needed setup code for the final chart.

### Repository + DataSource

It was built a **`BitcoinRepository.kt`** which consumes from **`BlockchainRemoteDataSource.kt`** that provides with all Bitcoin information from **`BlockchainAPI.kt`** using **Retrofit**.

A separate ***interface DataSource*** was created because it was planned to create a *LocalDataSource* to be able to save some values locally in a Room database. Unfortunately this was not possible to accomplish in the given time.

### Data Models
In order to keep the UI model (domain model) separate from any DTO model, I created 2 different types of models:
  * **`BitcoinRecordInfo`** & **`BitcoinValue`** as domain models. `BitcoinRecordInfo` to display some general info in the `BitcoinValueFragment`, and `BitcoinValue` to display values in the chart.
  * **`BlockchainResponseDTO`** & **`BitcoinValueDTO`** as the remote source models. Used to retrieve data from the `BlockchainAPI`.
  
Both of them make use of their own mappers which are used in the Repository layer to map the DTOs to the domain model when retrieving the Bitcoin info.

## Conclusion 

### Resources
Find attach to the project folder a [Debug APK](app-debug.apk).
In addition, a few screenshots and a demo video can be found also in the project folder: 
- Bitlue_DemoVideo.mp4
- Bitlue_LightTheme_HomeScreen.png
- Bitlue_LightTheme_FilterSettings.png
- Bitlue_DarkTheme_HomeScreen.png
- Bitlue_DarkTheme_FilterSettings.png

### Final words
I hope you could enjoy my work and could appreciate the effort in it.

Don't hesitate to ask any question if you have. 
