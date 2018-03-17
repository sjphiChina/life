#!/bin/sh

echo "********************************************************"
echo "Starting Life User Server via Eureka"
echo "********************************************************"
java -Djava.security.egd=file:/dev/./urandom -Dserver.port=$SERVER_PORT   \
     -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI             \
     -Dspring.zipkin.baseUrl=$ZIPKIN_URI                                  \
     -jar /usr/local/life/website/@project.build.finalName@.war
