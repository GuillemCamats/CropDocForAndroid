# CropDocForAndroid

CropDoc is an Android application to help companies or competent authorities and farmers to detect certain crop illnesses on the terrain and display the localization of the trees and terrains affected by the crop illness on the liquid galaxy to help them monitor the evolution of such illnesses throughout the territory. 

The problem is solved through an android application with a machine learning model that detects the illness of the samples. After that the user can store the location of the sample. The coordinates can be displayed on the Liquid Galaxy to visualise all the data collected from the user.  

## Running with apk

### Prerequisites
- Android device

### Dowload from Playstore

Dowload the apk from the [Playstore](https://play.google.com/store/apps/detailsid=com.galaxy.cropdoc&hl=es_419&gl=US)

### Using the app

#### Analize photo Button

When the user presses this button on the main page of the app he is redirected to the page where the data is analyzed. By pressing the button loads picture, the user loads a picture from the gallery. An then by pressing confirm picture, the machine learning model will analyze the picture and give an output prediction.

After this the user can save the location from where the tree had been analyzed. 

#### Send Kmls Button

When the user presses this button he is redirected to the page where the locations saved. And from there the user can connect to the Liquid Galaxy and send the data, thus has been created for the user. 

#### Send Demos Button 

It's the same that send kmls, but with pre created demos. 


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
