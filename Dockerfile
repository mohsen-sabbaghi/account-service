#Start with a base image containing Java runtime
FROM openjdk:11-slim as build

# Add Maintainer Info
LABEL maintainer="Mohsen Sabbaghi <mohsen.sabbaghi@gmail.com> "

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
COPY ${JAR_FILE} account-service.jar

ENTRYPOINT ["java","-jar","/account-service.jar"]