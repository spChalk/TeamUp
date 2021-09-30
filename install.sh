#!/bin/bash

./mvnw clean install -DskipTests

docker build -t teamup.jar .

docker-compose up 

