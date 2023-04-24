#!/bin/bash

java -jar backend-1.0.jar &
sleep 5
apachectl -D FOREGROUND