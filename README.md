# CropDocForAndroid

## Running from source

### Prerequisites

- Android Studio, Visual Studio Code or another IDE that supports Java and Kotlin development.
- Android SDK
- Android device or emulator
- Git

### Step 1: Clone the repository
The full code is located in this GitHub repository. To clone the project via terminal use:

> git clone https://github.com/GuillemCamats/CropDocForAndroid

### Step 2: Set up Google Maps API Key

CropDoc uses [Google Maps Android API](https://developers.google.com/maps/documentation/android-sdk/start?hl=es-419) as the map service. To use Google Maps you required an API KEY. To get this key you need to:

1.Have a Google Account

2.Create a Google Cloud Project

3.Open Google Cloud Console

4.Enable Maps Android SDK

5.Generate an API KEY

With the key in hands, the next step is placing the key inside the app. Go to local.propieties and put this line at the end of the file.

GOOGLE_MAPS_API_KEY="PLACE_HERE_YOUR_API_KEY"
