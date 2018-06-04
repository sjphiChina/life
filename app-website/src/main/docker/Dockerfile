FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/life/app-website
ADD  @project.build.finalName@.war /usr/local/life/app-website
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
