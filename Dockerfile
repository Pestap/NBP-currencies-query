FROM ubuntu:23.04

RUN apt-get update
RUN apt-get install -y default-jre
RUN apt-get install -y apache2

COPY bash_start_container.sh bash_start_container.sh
COPY backend/target/backend-1.0.jar backend-1.0.jar
COPY frontend /var/www/html

CMD ./bash_start_container.sh


EXPOSE 80
EXPOSE 8080
