# DunzoTask
The project is a simple search app which uses flickr api to show some images. Apart from the simple call from network to display a list, some other extra features have also been added which includes, rotation handling and scroll to the position where you left off before your device got rotated. No extra network calls are made while rotating. Also, after rotation the user stays in the position to which he/she scrolled to. Local DB has been used to store the search history. 
Search history gets stored in the device. Comments have been added in the code where its needed. First time when search button is clicked, the search history is not saved as the list which is used to save the search history data is populated when a response of the network call is saved. This app follows all the SOLID principles.

This is a self-explanatory document that explains/documents on what libraries are used in the project and why they are used even though there are other options available. Libraries that are used in the project are : 

1. Glide for image loading : Glide is hugely popular and apart from that also gives us many features like caching the image for faster retrieval. Another option could have been Picasso which takes up less memory and is CPU-efficient. However, why I chose Glide is because it supports GIF as well as locally stored videos which is not available in Picasso. Thus using Glide helps us give extendability for our app if at a later point of time we decide to include videos/gif.

2. Retrofit for network calls : Volley could have been a better option here, since Volley has caching mechanism and Retrofit doesn't. However using POST requests alongwith Multipart to send files, Retrofit gives us an edge over Volley. Implementing Multipart POST in Retrofit is easier and intuitive than Volley. In Volley we would have to convert the Java classes manually to JSON objects for POST request and some additional code for Multipart requests. In addition to that, Volley supports only 4 network response types versus Retrofit which supports 6-7 response types (mostly the ones we use extensively like String, Object, Boolean, etc,).
Supporting such a wide variety of types especially the multipart post request types, this app can be extended to support image search types as well.

3. Hilt for dependency injection : Dagger 2 is the most widely used dependency injection library compared to Hilt which is very new and has beem released recently. The reason for using Hilt in place of Dagger 2 is because it runs on top of Dagger2 and removed almost all of the boilerplate code that we manually write for all the modules and components classes. Using Hilt not only lets us leverage the power of Dagger2 but also lets us achieve the same functionality with lesser boilerplate code.

4. ObjectBox for storing local data : Here also, we have options like SQLite, Realm and the hugely popular Room database. The reason for choosing ObjectBox here is because for large datasets, Object Box gives better performance for CRUD operations than Room and Realm. Although the one downside is Object Box takes up about 1-2 MB of the apk's size compared to Room which takes up about only 50 KB as it is simply a wrapper over SQLite. However, Object Box not only gives better performance but also supports reactive programming out of the box by supporting reactive streams to listen and update to changes in local data. This helps us making our app reactive without needing to use Rx library(which again takes up a lot of space).

5. RxJava and RxKotlin : Here we could have used coroutines in place of Rx but the reason for using Rx here is for making the network calls reactive. Also, coroutines were created with a single purpose in mind ,i.e., making tasks asynchronous while Rx is a more general tool that gives us a wide variety of options for making our code asynchronous as well as reactive. By having such a wide variety of toold available in our hands, Rx gives us better flexibility as well as extendability also considering the fact that Rx also has a better support for socket programming as compared to coroutines.

The app uses Clean architecture alongwith MVVM design pattern. The reason for choosing MVVM design pattern is because of its support from the Android Jetpack library as well as in helping us painlessly retain data when configuration changes happen. The reason for choosing Clean architecture is because it helps make the app scalable, extendable, testable and easily maintainable. It not only provides good decoupling of the code but also helps us write cleaner code. As a result, our network calls are separated from the views and our business logic is independent of the other outer layers. I have divided the app into packages, namely, the domain, data, di, ui and the utils package. These packages are explained below :

Please refer to this link for the onion layer architecture : https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg

1. Domain Package : Domain Package is the innermost layer of the app and contains the app's businesss logic. This is the independent part of the app and is independent of the other layers. Here domain package uses the ObjectBox class in the utils package. That is ok because its a singleton class which is initialised at the application level and is provided with the application level context. The domain package contains these packages : 
 
 a. Entities : This package contains all the business level entities. Here entities are model classes which provide a blueprint to store the actual objects that are received from the network or the objects that are stored in the local database.
 
 b. Repository package : Repository package contains the repository contracts(interfaces) that the use cases should use to provide the data. These contracts(interfaces) are fulfilled(implemented) by the implementation classes in the data package.
 
 c. Requests package : Requests package contains the various requests that have to be provided when making a network call. Requests are the parameters that need to be provided to the network request to get some data in response.

 d. UseCases package : This package contains the use cases that are used to communicate and pass data from the domain layer to the UI. UI communicates with the data via the domain's use cases.
 
2. Data Package : Data package is the second innermost layer of the onion layer after the Domain package. This layer contains all the application specific data, services and implementations. This package is abstracted from the UI layer with the help of use cases. The packages inside data package are : 

 a. Constants package : This contains all the app-level constants that need to be used throughout the app irrespective of whether its used in the data layer or the UI layer. However for the domain layer specific constants, they need to be defined in the domain package itself.

 b. Implementation package : This package contains all the actual implementation of the contracts inside the Repository package. The code inside this package should be abstracted from the UI layer. 
 
 c. Service package : This package contains all the services that will be using the Urls provided to make a network call and get data and communicate it back to the UI via the implementation class and its corresponding use case. Services should not be necessarily only be making network calls but also can be used to do CRUD operations from local database.

3. DI package : This package contains all the dependency injections. This package resolves all the dependencies needed by Hilt to resolve dependencies in the whole app.

4. Utils package : This package contains the necessary files for doing some special kind of work required in the whole app. This package contains all the global level code that might be needed in more than one place.

5. UI package : This package contains all the activities, fragments, viewmodels, viewholders and adapter packages. These packages contain all the UI related classes required by the app to show the fetched/stored data to the user.
