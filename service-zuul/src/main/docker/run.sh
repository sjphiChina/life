#!/bin/sh

#echo "********************************************************"
#echo "Waiting for the eureka server to start on port $EUREKASERVER_PORT"
#echo "********************************************************"
#while ! `nc -z eurekaserver  $EUREKASERVER_PORT`; do sleep 3; done
#echo "******* Eureka Server has started"

#echo "********************************************************"
#echo "Waiting for the configuration server to start on port $CONFIGSERVER_PORT"
#echo "********************************************************"
#while ! `nc -z configserver $CONFIGSERVER_PORT`; do sleep 3; done
#echo "*******  Configuration Server has started"

echo "********************************************************"
echo "Starting Zuul Service with $CONFIGSERVER_URI"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
     -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI   \
     -jar /usr/local/life/service-zuul/@project.build.finalName@.jar
