# MovieApp
 A demo Android app uses themoviedb API to display a list of movies
## The demo works as follow :
1. The movies screen displays a list of movies
2. The movie preview screen which displays the clicked Movie item in a single view 

## Architecture pattern: 
MVVM with state model single activity architecture following the Data-Domain-Presentation clean architecture with repository pattern for data as a layer over different offline/online data sources
  
## Libraries and dependencies:
1. Constraint Layout for flexible relative positioning and sizing of views
2. Coroutines and flow for asynchronous operations (networking or data store operations)
3. view binding for binding views
4. glide for image loading
5. Room offline database
6.  Navigation component for handling transitions between fragments
7.  Recyclerview
8.  Dagger-Hilt for dependency injection
9.  Retrofit/okhttp for networking

