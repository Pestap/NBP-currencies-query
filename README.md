# NBP-currencies-query

## Technologies used
The app is implemented using Java Spring

The UI was created with simple JavaScript and HTML

## How to start the application

### Without Docker
Navigate to the project folder and run:
```console
mvn package
```

Then run the following command to start the application:
```console
java -jar target/backend-1.0.jar
```
The app is now working and you can use the UI from the frontend directory to test the app.

### Using Docker
The docker image of the application is avaliable on my public dockerhub registry (https://hub.docker.com/repository/docker/pestap/nbp-currencies-querries/general). It contains the Java Spring backend and the frontend hosted on a HTTP server.

To download the image run:
```console
docker pull pestap/nbp-currencies-querries:latest
```
To start a container with the application run:
```console
docker run -p 80:80 -p 8080:8080 pestap/nbp-currencies-querries
```
After the containter has started you can acccess the UI at http://localhost:80.

## How to use the application

### Using the UI

To use the access the provided UI, go to http://localhost:80 (if using docker) or open index.html in the frontend directory.

![obraz](https://user-images.githubusercontent.com/90579181/234541711-c08cbfff-0679-4f90-bbca-fa80d5fd00d6.png)

To exectue queries, enter the desired data (ex. currency code and date of quotation) and press "Submit".

![obraz](https://user-images.githubusercontent.com/90579181/234542059-d141ede1-2f56-4d0d-8bbe-750989a329d4.png)

Then, you will recieve the query answer in a form of an alert:

![obraz](https://user-images.githubusercontent.com/90579181/234542246-bd38302c-b2a1-40a6-82ee-123586292722.png)

The alert contains the query answer (as specified in the requirements) and data about the query (ex. currency code and date).

In case of Tasks 2 and 3 the alert contains the answer and corresponding dates of quotation.

![obraz](https://user-images.githubusercontent.com/90579181/234542807-0d01f043-6b6b-4159-a078-b802d014e7cb.png)

In case of any errors (ex. invalid currency code, no quotations associated with desired date, service unavaliable, ...) the alert will inform the user about the error.

![obraz](https://user-images.githubusercontent.com/90579181/234546600-90cb6c89-a298-452b-bb4c-85b9e8ff33aa.png)
![obraz](https://user-images.githubusercontent.com/90579181/234549146-ae983289-daaa-4d8c-a38f-c3248be6d5f1.png)


### Using the command line

To use the application without the UI, the user can send querries to http://localhost:8080/currencies/ endpoint.

Example querries for each task:

Task 1
```console
curl http://localhost:8080/currencies/EUR/2023-04-25
```

Result:

![obraz](https://user-images.githubusercontent.com/90579181/234552138-3a63a1d2-53c3-4c8a-9553-14d929978f4b.png)

Task 2
```console
curl http://localhost:8080/currencies/EUR/minAndMax/10
```

Result: 

![obraz](https://user-images.githubusercontent.com/90579181/234551931-b7581fd0-ff30-4765-b012-adbbb0f11ebe.png)

Task 3
```console
curl http://localhost:8080/currencies/EUR/maxDifference/10
```

Result:

![obraz](https://user-images.githubusercontent.com/90579181/234552386-7a0e8a3e-0e50-4b70-bacd-bed36483a62a.png)


#### The endpoints exposed by the application:

Task 1: http://localhost:8080/currencies/{currencyCode}/{date}

Task 2: http://localhost:8080/currencies/{currencyCode}/minAndMax/{numberOfQuotations}

Task 3: http://localhost:8080/currencies/{currencyCode}/maxDifference/{numberOfQuotations}








