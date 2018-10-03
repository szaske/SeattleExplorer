This repo represents my attempt to create a proper Android app. By proper I mean an Android app built following Google's recommended approach, which means:

1. Follow clean architecture principles
2. Use the MVVM design pattern
3. Use Google's Jetpack components Navigation and Fragments for UI
4. Use Room for local storage
5. Use Dagger2 for dependency injection.
6. Use Google Firebase as my online storage.

The app I'm creating is called Seattle Explorer. It will list interesting locations around the city of Seattle.

I'll document each step here in the README.md to help me learn and remember the steps involved and the problems I've run into along the way. I've found my brain remembers coding concepts better when I force myself to add comments to my code as I'm coding. I'll track my progress using branches, so anyone can follow along with my progress. There's plenty of MVVM sample code projects out there, but I've often found them difficult to follow. The samples are often working apps in their final working state. But how did they get from start to finish? With this project I'll attempt to document the process from start to finish.

Finally, if anyone using this repo/code find bugs please submit an issue using Githubs issue tracking.

**MY STARTING POINT**
I consider myself a beginner Android developer. I've written one large-ish Android application. My application was fairly complex; I wrote custom views, used Google location services and utilized Firebase as my backend. But I don't have any experience with Fragments, Databinding or Dagger and like most beginner developers most of my code ended up in the Activity. Now it's time I grow as a developer and learn the proper way to architect an Android app.

**STEP #1. Creating a new Android project.** I'm using Android Studio 3.2. I created a new Android project, gave it a name (Seattle Explorer), chose a minimum SDK (21 Lollipop) and when asked to chose an activity type, I chose Fragment+Viewmodel. I chose Fragment+ViewModel because it's a new option added to help facilitate the use of Fragments and ViewModels, two things I'll want to add to my project. I'm hoping the boilerplate code will be helpful. WOOHOO I have a working Android app with fragments already. I added this README.md file and this will be my first commit.

**STEP #2. Basic project clean up.** I've created enough Android apps to know that it's nice to have some organization in my build.gradle files. I'll start by consolidating all my versions numbers in the project build.gradle file. This it pretty standard, just create an ext section within the buildscript section. I also lowered the compileSdkVersion to 27 so my app will work with more phones, but still work with the Jetpack Navigation library (which requires AppCompat 27+). It's best to do this at the start of a project before I add any code.

**STEP #3: Configuring Jetpack Navigation.** We'll we have a working Android app, with a Fragment and viewModel but I need to convert this boilerplate code to instead be using Google's new Navigation library. To configure Navigation you need to do the following:
* Make sure you're using the latest version of Android 3.2.
* In Android Studio visit FILE -> SETTINGS and go to the Experimental tab. Make sure "Enable Navigation Editor" is enabled. 
* Add the two new dependencies for Navigation they are: android.arch.navigation:navigation-fragment, and android.arch.navigation:navigation-ui
* Add the navigation-safe-args-gradle-plugin Gradle plugin in the Project build.gradle.

**STEP #4: Setting up Navigation.** Now that Navigation is configured, I'll go ahead and create a basic setup with two fragments and viewmodels. Here are the substeps needed:
* Start by adding a navigation graph. You do this by right-clicking the res folder and selecting NEW -> ANDROID RESOURCE FILE, naming it nav_graph and for resource type selecting "Navigation". That will create a navigation resource folder and create this new XML file.
* I created 2 new fragments using the "Create Blank Fragment" tool within the navigation editor. The only thing I added was the code to instantiate a viewmodel within each fragment.
* Back in the editor I added both fragments to the graph, made StartFragment the Starting fragment, and then connected the two fragments with an action. For more information see (https://codelabs.developers.google.com/codelabs/android-navigation/#0)
* Next you need to add a placeholder view within the layout of your Activity. This fragment view Id is nav_host_fragment.
Finally to test the functionality of my viewModels I added a simple link in the PoiDetailFragment from it's viewModel

**STEP #5. Creating the Domain layer.** Now that I have a working Android app with a simple navigation graph. I'm going to start adding a bit more complexity to my existing app. Here is the architecture I'm building: ![Imgur](https://i.imgur.com/e7xHP9Q.png)

The domain layer will host use case classes. These use case classes will represent information needed by the presentation layer, AKA viewmodels. Right now my apps PoiDetailViewModel is creating detailMessage string resource itself but in it's final form the presentation layer would get this information from the domain layer. I'll go ahead and alter the app so the domain layer is passing this information into presentation layer (via the PoiDetailViewModel). I just created the Domain package and added a GetPois class. POI is short for "Point of Interest" one of the main data types I'll be using in the application. At the moment my class has just one function, getPoiDetailMessage as I'm trying to keep it as simple as possible. In the viewModel I now instantiate a GetPois class and ask it for the detailMessage string. WooHoo! I now have a working Android app with a presentation layer consisting of viewmodels and fragments (managed by a NavController) and a domain layer that is passing information into the viewmodel. However now I'm breaking one of the main tenants of clean architecture. The Presentation layer code has a dependency on another external layer, the domain layer. To fix this problem I'll need to re-organize my Viewmodel classes and move the dependencies into the constructors and a that means dependency injection. Dagger time.

**STEP #6. Setting up basic Dagger Dependency Injection.** Here is an overview of all the steps needed to configure Dagger DI and getting it working in my project:
* Add the Dagger dependencies (see the app build.gradle file) to the project. There are a total of 5 dependencies required. 
* Create an Application class. As someone unfamiliar with Dagger here was the first place I initially got confused about the Dagger setup. I wasn't sure if I needed to implement ActivityInjection if I wasn't planning to inject anything into an Activity. Could I stick to FragmentInjection only? After some experimentation it seems ActivityInjection was required. Okay then, my application class would implement both HasActivityInjector and HasFragmentInjector. Now I ran into my second problem. The AndroidInjection.inject(this) method was complaining that my Fragment was not a true Fragment, at lease not according to the AndroidInjection class. It seems Dagger has 2 versions of the Fragment injector. This wasn't documented very well in the Dagger documentation so I'm guessing a bit here, but apparently if you're using the Support library you need to use the Support injector instead. So instead of implementing HasFragmentInjector I need to instead implement HasSupportFragmentInjector and use AndroidSupportInjection.inject. After that change everything was working correctly.
* Create the AppComponent interface. This is the interface Dagger uses to configure your Dagger injection classes.
* Create various DI Modules. These classes are used by Dagger to create the Dagger injection classes. The ActivityModule and FragmentModule are used to tell Dagger where injection begins. 
* Create the ViewModel module and factory. Daggers default viewmodel factory apparently only works with empty constructors, but since I plan to pass in Domain parameters to my viewmodels we'll need to update the viewModel factory.  This improved ViewModel factory design pattern has been used often in other Android apps.
* Inject the GetPois class.  The whole point of this step was to be able to inject my GetPois class into the PoiDetailViewModel.  So I altered the PoiDetailViewModel class, adding the @Inject constructor and moving GetPois to the constructor.  Finally I also altered the GetPois class to add an empty @Inject constructor.  This tells Dagger that it can instantiate the GetPois class

After all of this work what is Dagger able to inject into my app?  Anything with the @Inject annotation.  That means:
* ViewModelFactory (from PoiDetailFragment)
* GetPois (from PoiDetailsViewModel)

At this point I have a working Android app with a presentation layer consisting of fragments and viewmodels, app navigation controlled via the Android JetPack Navigation library, a super simple Domain layer and dependency injection via Dagger.  Time for a commit.

**STEP #7. Flesh out the Domain layer.**  The domain layer will house my business logic.  This business logic will be broken down into usecase classes following the clean architecture pattern.  Also my plan is to use the Observable pattern to move data between my repository layers and up to the Domain layer.  The Domain layer will send the Observables data along to the ViewModel, where the viewModel will convert it to LiveData, so that it's easier to present in the views.  With that in mind here are the steps required to flesh out the Domain layer:
* Installing the ReactiveX dependencies.  This should be a pure kotlin layer, so all I'll need is io.reactivex.rxjava2:rxkotlin
* POI_domain model.  Poi's are one of the main entities I'll be using in the app.  Since I want to follow the clean architecture principles the layers should not share any knowledge about each other.  That means a POI in the domain layer could be different than a POI in the presentation layer or Repo layer. That means that I need to have separate POI models for each layer.  I tried to determine if there is any sort of standard naming convention for this, since I really don't want to use the same name for the models as that might get confusing.  So I'm going to suffix each model with the name of the layer.  So my Poi model in the domain layer will be called poi_domain.  I'll need to create mapper classes in each layer to convert these similar model classes.
* Threading responsibilities.  Using RxJava means I'll need to manage 2 threads, where I subscribe on and where I observe on.  But what layer should handle this logic?  According to [this article](https://medium.com/@yoelglus/threading-strategies-on-android-with-clean-architecture-57f6d3714d96) the Viewmodel and repository should.  I'm not so sure and instead chose the Domain class to handle this, but it's definitely a point of contention in the industry.  With that decision made I'll create an interface called ObservingThread so I can get a link to the main thread from the Presentation layer in the domain layer
* ObservableUseCaseBase.  To eliminate some of the boilerplate code, I'll create a base class for my UseCase classes.  Credit to [Joe Birch](https://twitter.com/hitherejoe) for showing me this pattern.  The base class is specifically for Observable return types.  There is a lot of stuff happening there so feel free to check out the file.
* The Repository Layer.  To really flesh out the Domain layer I need to do some refactoring starting with the creation of the Repository layer.  I created an interface for repos called ExplorerRepository and created a simple stand-in repository so that I can test my domain code.  I also created some helper utility classes to help me fake a repo.  Next I refactored my getPois usecase class to inherit from ObservableUseCaseBase.  This is getting to be too much work for one step (and one commit).  So I'll go ahead and stop and commit these code changes before I continue.

**STEP #8. Refactoring the Presentation layer.**  So far my test data was a simple string passed along to the Presentation layer.  Now I've moved on to an Observable data type and need to alter the Presentation layer to be able to accept it.  Here are my tasks in this step:
* I'll start with a minor change, renaming the ui package presentation.
* Then I'll create a Poi_Presentation model (as describe earlier)
* Subscriber class. Accepting an RxJava observable means the viewmodel needs to act as the observer and subscribe to the Domain layers getPois Observable stream. I'll create a inner class in the viewmodel to handle this.
* Mapper class. Note in the PoiSubscriber classes onNext method I'm using a mapper class to convert Poi-Domain's to Poi_presentations.  This required a new PoiMapper class
* I need the Viewmodel to execute the use case.  This action is the viewmodel asking for this observable data from the domain layer.  I accomplished this by adding an init block.
* LiveData.  Since I want to take advantage of Google's LiveData component, here in the ViewModel I convert the incoming RxJava Observable data into LiveData.  So I create a private variable to store a reference to this MutableLiveData and in the PoiSubscriber classes OnNext method I'm posting values to the LiveData stream.
* Disposables.  Since the Viewmodel is acting as a subscriber to the Domains RxJava stream, I need to make sure to dispose of the subscription properly.  So I override the onCleared method and added a dispose call there.
* Observing LiveData.  Finally in the fragment itself I created an observer to the LiveData, and grabbed the name of the first item in my Poi list.

Great work so far.  I now have a working Android app using the MVVM design pattern, RxJava, a domain and simple repository layer...navigation, fragments and viewmodels. 