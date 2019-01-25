# buy-anywhere-product-catalog
This is the Microservices that allows to create the catalog of products

In order to work with this project you should start by having a "buy-anywhere" folder in your system (preferable in c:\buy-anywhere), inside you can execute:
<br>
<br>
`git clone https://github.com/maximilianotopp/buy-anywhere-product-catalog.git`
<br>
<br>
You need the following software in order to edit and run the project:
<br>
[IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows)<br>
[Maven](https://maven.apache.org/download.html)<br>
[Java SDK v8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
<br>
<br>
This project was built using [Spring Boot](http://spring.io) using Maven
<br>
<br>
 In order to import this project in IntelliJ IDEA, open it and select "Import".
 <br>
 <br>
 If you want to see what endpoints are available and test them locally, just enter this URL in your browser: <br>
 `http://localhost:8080/api/swagger-ui.html`
 
 All controllers go into the folder `controllers` and models go into the folder `models`.
 <br>
 <br>
 There is a test controller (`GreetingController`) that serves as an example on how to create a new controller and how the different paths work.
 <br>
 <br>
 ## Endpoints rules:
 When you create an endpoint you must respect REST conventions. This means that, if you have an endpoint that manages the CRUD of a Car class then the controller name will be CarController (singular). If you need an enpoint that returns all cars then the Endpoint will be CarsController.
 <br><br>
 The example controller (`GreetingController`) was made with this conventions in mind.