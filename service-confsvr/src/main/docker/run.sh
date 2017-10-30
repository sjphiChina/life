#!/bin/sh
echo "********************************************************"
echo "Starting Configuration Server"
echo "********************************************************"
java -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI -jar /usr/local/life/confsvr/@project.build.finalName@.jar
