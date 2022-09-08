# CropDocForAndroid


## Running with apk

### Prerequisites
- Android device

### Dowload from Playstore

Dowload the apk from the [Playstore](https://play.google.com/store/apps/detailsid=com.galaxy.cropdoc&hl=es_419&gl=US)

### Using the app

#### Analize photo Button

When the user press this button in the main page of the app he is redirected to the page where the data is analized. By pressing the button load picture, the user loads a picture from galery. An then by pressing confirm picture, the machine learning model will analyze the picture and give an output prediction. 

After this the user can create a location to store where is the tree that has been analized. 

#### Send Kmls Button

When the user press this button he is redirected to the page where the locations saved. And from there the user can connect to the Liquid Galaxy and send the data thas has been created from the user. 

#### Send Demos Button 

It's the same that send kmls, but with precrated demos. 


## Running from source

### Prerequisites

- Android Studio, Visual Studio Code or another IDE that supports Java and Kotlin development.
- Android SDK
- Android device or emulator
- Git

### Step 1: Clone the repository
The full code is located in this GitHub repository. To clone the project via terminal use:

` git clone https://github.com/GuillemCamats/CropDocForAndroid `

### Step 2: Set up Google Maps API Key

CropDoc uses [Google Maps Android API](https://developers.google.com/maps/documentation/android-sdk/start?hl=es-419) as the map service. To use Google Maps you required an API KEY. To get this key you need to:

1.Have a Google Account

2.Create a Google Cloud Project

3.Open Google Cloud Console

4.Enable Maps Android SDK

5.Generate an API KEY

With the key in hands, the next step is placing the key inside the app. Go to local.propieties and put this line at the end of the file.

` GOOGLE_MAPS_API_KEY="PLACE_HERE_YOUR_API_KEY" `

### Contributing

Fill up issues, bugs or feature requests in our issue tracker. Please be very descriptive and clear so it is easier to help you.

If you want to contribute to this project you can open a pull request at time you like.
### Licens

Copyright 2021 [Guillem Camats Felip](https://www.linkedin.com/in/guillemcamats/)
