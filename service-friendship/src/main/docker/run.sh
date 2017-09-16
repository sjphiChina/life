#!/bin/sh
echo "********************************************************"
echo "Waiting for the lifeconfsvr server to start on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z lifeconfsvr $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"


echo "********************************************************"
echo "Starting Life Friendship Server"
echo "********************************************************"
java -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.profiles.active=$PROFILE -jar /usr/local/life/friendship/@project.build.finalName@.jar
