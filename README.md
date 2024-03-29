
## Simple application for saving IoT data from Kafka, saving data in database and querying data via API.

Application listens to 3 Kafka topics: OutsideTemperature, OutsideHumidity, WindSpeed. 
Contracts for topics can be found [here](https://github.com/santik/iot-browser/tree/master/src/main/resources/schema)

Application saves data from topics to the database. In-memory H2 database was used. Data structure is flat and no-relational, so NoSQL database can be used in a real use case.

Application exposed API endpoint is `/readings/`.   
JSON must be sent as a body for the POST request.
Contracts for request and response can be found [here](https://github.com/santik/iot-browser/tree/master/src/main/resources/schema)  
*[Postman collection for API calls](https://github.com/santik/iot-browser/blob/master/relay42.postman_collection.json)*
  
### Dependencies
The full dependency list can be seen in [pom.xml](https://github.com/santik/iot-browser/blob/master/pom.xml)
Bellow listed the main set of dependencies

 - Java11
 - Maven
 - SpringBoot
 - SpringCloudStream
 - JUnit
 - Mockito
 - JBehave
 - Serenity
 - H2
 
 Kafka cluster for the local running of application and functional testing created with [docker-compose](https://github.com/santik/iot-browser/blob/master/docker-compose.yml) 

### Running

 1. Checkout repository
 2. Run `docker-compose up -d` to create Kafka and Redis containers
 3. Run `mvn clean package`

The easiest way is to run it inside [IntelliJ IDEA](https://www.jetbrains.com/idea/). 
Steps :

 4. Run `src/main/java/com/relay42/browser/IotBrowser.java`

If there is no IDEA. 

 4. Run `java -jar target/iotbrowser-DEVELOP-SNAPSHOT.jar` 
 
 ### Testing
 
 Majority of classes are covered with unittests.
 
 Functional flows are covered with functional tests. Serenity BDD approach was used to have [tests as documentation](https://en.wikipedia.org/wiki/Specification_by_example). 
 Stories can be found [here](https://github.com/santik/iot-browser/tree/master/src/test/resources/com/relay42/functional/stories) 
 
 For _almost_ real testing `sends_random_kafka_messages.story` was created. It sends 3 different messages every second in infinite loop. It is possible to run this story and make calls to application via Postman. 
 ### Known issues and possible improvements
  - Different types of devices can have the same id or can be members of the same group. There are 2 major ways to solve this problem:
    - send type of device in the request. So only reading from devices with particular type will be fetched.
    - put responsibility of providing unique ids and valid group ids to kafka messages producer (chosen)
  - Max, min, average, etc. can be done via database function(if engine supports of course). But I see it as coupling to DB engine. That's why I decided to calculate values in app from the list of models.