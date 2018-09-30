This repo represents my attempt to create a proper Android app.  By proper I mean an Android app built following Google's recommended approach, which means:

1. Follow clean architecture principles - Use the MVVM design pattern
2. Using Google's Jetpack components Navigation and Fragments for UI
3. Using Google's Jetpack components Room for local storage
4. Using Dagger2 for dependency injection.
5. Using Google Firebase as my online storage.

The app I'm creating is called Seattle Explorer.  It will list interesting locations around the city of Seattle.

I'll document each step here in the README.md to help me learn and remember the steps involved and the problems I've run into along the way.  I've found my brain remembers coding concepts better when I force myself to add comments to my code as I'm coding.  I'll track my progress using branches, so anyone can follow along with my progress.  There's plenty of MVVM sample code projects out there, but I've often found them difficult to follow.  The samples are often working apps in their final working state.  But how did they get from start to finish?  With this project I'll attempt to document the process from start to finish.

Finally, if anyone using this repo/code find bugs please submit an issue using Githubs issue tracking.

**MY STARTING POINT**
I consider myself a beginner Android developer.  I've written one large-ish Android application.  My application was fairly complex; I wrote custom views, used Google location services and utilized Firebase as my backend. But I don't have any experience with Fragments, Databinding or Dagger and like most beginner developers most of my code ended up in the Activity. Now it's time I grow as a developer and learn the proper way to architect an Android app.

**STEP #1.  Creating a new Android project.**  I'm using Android Studio 3.2.  I created a new Android project, gave it a name (Seattle Explorer), chose a minimum SDK (21 Lollipop) and when asked to chose an activity type, I chose Fragment+Viewmodel.  I chose Fragment+ViewModel because it's a new option added to help facilitate the use of Fragments and ViewModels, two things I'll want to add to my project.  I'm hoping the boilerplate code will be helpful.  WOOHOO I have a working Android app with fragments already.  I added this README.md file and this will be my first commit.

**STEP #2. Basic project clean up.**  I've created enough Android apps to know that it's nice to have some organization in my build.gradle files.  I'll start by consolidating all my versions numbers in the project build.gradle file.  This it pretty standard, just create an ext section within the buildscript section.  I also lowered the compileSdkVersion to 27 so my app will work with more phones, but still work with the Jetpack Navigation library (which requires AppCompat 27+).  It's best to do this at the start of a project before I add any code.

**STEP #3: Configuring Jetpack Navigation.**  We'll we have a working Android app, with a Fragment and viewModel but I need to convert this boilerplate code to instead be using Google's new Navigation library.  To configure Navigation you need to do the following:
* Make sure you're using the latest version of Android 3.2.
* In Android Studio visit FILE -> SETTINGS and go to the Experimental tab.  Make sure "Enable Navigation Editor" is enabled. 
* Add the two new dependencies for Navigation they are: android.arch.navigation:navigation-fragment, and android.arch.navigation:navigation-ui
* Add the navigation-safe-args-gradle-plugin Gradle plugin in the Project build.gradle.

**STEP #4: Setting up Navigation.**  Now that Navigation is configured, I'll go ahead and create a basic setup with two fragments and viewmodels.  Here are the substeps needed:
* Start by adding a navigation graph.  You do this by right-clicking the res folder and selecting NEW -> ANDROID RESOURCE FILE, naming it nav_graph and for resource type selecting "Navigation".  That will create a navigation resource folder and create this new XML file.
* I created 2 new fragments using the "Create Blank Fragment" tool within the navigation editor.  The only thing I added was the code to instantiate a viewmodel within each fragment.
* Back in the editor I added both fragments to the graph, made StartFragment the Starting fragment, and then connected the two fragments with an action.  For more information see (https://codelabs.developers.google.com/codelabs/android-navigation/#0)
* Next you need to add a placeholder view within the layout of your Activity.  This fragment view Id is nav_host_fragment.
Finally to test the functionality of my viewModels I added a simple link in the PoiDetailsFragment from it's viewModel

**STEP #5. Creating the Domain layer.**  Now that I have a working Android app with a simple navigation graph. I'm going to start adding a bit more complexity to my existing app.  Here is the architecture I'm building: ![Imgur](https://i.imgur.com/e7xHP9Q.png)

The domain layer will host use case classes.  These use case classes will represent information needed by the presentation layer, AKA viewmodels.  Right now my apps PoiDetailsViewModel is creating detailMessage string resource itself but in it's final form the presentation layer would get this information from the domain layer.  I'll go ahead and alter the app so the domain layer is passing this information into presentation layer (via the PoiDetailsViewModel).  I just created the Domain package and added a GetPois class.  POI is short for "Point of Interest" one of the main data types I'll be using in the application.  At the moment my class has just one function, getPoiDetailMessage as I'm trying to keep it as simple as possible.  In the viewModel I now instantiate a GetPois class and ask it for the detailMessage string.  WooHoo!  I now have a working Android app with a presentation layer consisting of viewmodels and fragments (managed by a NavController) and a domain layer that is passing information into the viewmodel.  However now I'm breaking one of the main tenants of clean architecture.  The Presentation layer code has a dependency on another external layer, the domain layer.  To fix this problem I'll need to re-organize my Viewmodel classes and move the dependencies into the constructors and a that means dependency injection.  Dagger2 time.