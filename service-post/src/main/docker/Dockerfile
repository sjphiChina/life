FROM openjdk:8-jdk-alpine
RUN  apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/life/service-post
ADD  @project.build.finalName@.jar /usr/local/life/service-post
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
