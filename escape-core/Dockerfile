# Base image
FROM openjdk:11-jre-slim

# Add Maintainer Info
LABEL maintainer="tpal071997@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8081 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=app/escape-core.jar

# Add the application's jar to the container
ADD ${JAR_FILE} escape-core.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/escape-core.jar"]
