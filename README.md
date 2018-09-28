This repo represents my attempt to create a proper Android app.  By proper I mean an Android app built following Google's recommended approach, which means:

1. Follow clean architecture principles - Use the MVVM design pattern
2. Using Google's Jetpack components Navigation and Fragments for UI
3. Using Google's Jetpack components Room for local storage
4. Using Dagger2 for dependency injection.
5. Using Google Firebase as my online storage.

The app I'm creating is called Seattle Explorer.  It will list interesting locations around the city of Seattle.

I'll document each step here in the README.md to help me learn and remember the steps involved and the problems I've run into along the way.  I've found my brain remembers coding concepts better when I force myself to add comments to my code as I'm coding.  I'll track my progress using branches, so anyone can follow along with my progress.  There's plenty of MVVM sample code projects out there, but I've often found them difficult to follow.  The samples are often working apps in their final working state.  But how did they get from start to finish?  With this project I'll attempt to document the process from start to finish.

Finally, if anyone using this repo/code find bugs please submit an issue using Githubs issue tracking.

MY STARTING POINT
I consider myself a beginner Android developer.  I've written one large-ish Android application.  My application was fairly complex; I wrote custom views, used Google location services and utilized Firebase as my backend. But I don't have any experience with Fragments, Databinding or Dagger and like most beginner developers most of my code ended up in the Activity. Now it's time I grow as a developer and learn the proper way to architect an Android app.

STEP #1.  Creating a new Android project.  I'm using Android Studio 3.2.  I created a new Android project, gave it a name (Seattle Explorer), chose a minimum SDK (21 Lollipop) and when asked to chose an activity type, I chose Fragment+Viewmodel.  I chose Fragment+ViewModel because it's a new option added to help facilitate the use of Fragments and ViewModels, two things I'll want to add to my project.  I'm hoping the boilerplate code will be helpful.  WOOHOO I have a working Android app with fragments already.  I added this README.md file and this will be my first commit.

STEP #2. Basic project clean up.  I've created enough Android apps to know that it's nice to have some organization in my build.gradle files.  I'll start by consolidating all my versions numbers in the project build.gradle file.  This it pretty standard, just create an ext section within the buildscript section.  I also lowered the compileSdkVersion to 27 so my app will work with more phones, but still work with the Jetpack Navigation library (which requires AppCompat 27+).  It's best to do this at the start of a project before I add any code.

Step #3: Configuring Jetpack Navigation.  We'll we have a working Android app, with a Fragment and viewModel but I need to convert this boilerplate code to instead be using Google's new Navigation library.  To configure Navigation you need to do the following:
3a. Make sure you're using the latest version of Android 3.2.
3b. In Android Studio visit FILE -> SETTINGS and go to the Experimental tab.  Make sure "Enable Navigation Editor" is enabled. 
3c. Add the two new dependencies for Navigation they are: android.arch.navigation:navigation-fragment, and android.arch.navigation:navigation-ui
3d. Add the navigation-safe-args-gradle-plugin Gradle plugin in the Project build.gradle.

