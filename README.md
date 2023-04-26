# NBP-currencies-query

## Technologies used
The app is implemented using Java Spring

The UI was created with simple JavaScript and HTML

## How to run

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
After the containter has started you can acccess the UI at http://localhost:80



