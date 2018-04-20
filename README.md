# BakingApp


## Baking App Project Specification

### General App Usage
* Display recipes: app displays recipes from provided network resource.

* App Navigation: app allows navigation between individual recipes and recipe steps.

* Utilization of RecyclerView: app uses RecyclerView and handles recipe steps that include videos or images.

* App conforms to common standards: app conforms to common standards found in the [Android Nanodegree General Project Guidelines] (http://udacity.github.io/android-nanodegree-guidelines/core.html)

 

### Components and Libraries
* Master Detail Flow and Fragments: application uses Master Detail Flow to display recipe steps and navigation between them.

* Exoplayer(MediaPlayer) to display videos : application uses Exoplayer to display videos.

* Proper utilization of video assets: application properly initializes and releases video assets when appropriate.

* Proper network asset utilization: application properly retrieves media assets from the provided network links. It properly handles network requests.

* UI Testing: application makes use of Espresso to test aspects of the UI.

* Third-party libraries: application sensibly utilizes a third-party library to enhance the app's features. That is the helper library to interface with ContentProviders if recipes are stored locally, a UI binding library to avoid writing findViewById a bunch of times, or something similar.

 

### Homescreen Widget
* Application has a companion homescreen widget.

* Widget displays ingredient list for desired recipe.
