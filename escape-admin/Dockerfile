<<<<<<< Updated upstream
<<<<<<< Updated upstream
# Base image
FROM openjdk:11-jre-slim

# Add Maintainer Info
LABEL maintainer="tpal071997@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8081 available to the world outside this container
EXPOSE 8081

# The application's jar file
ARG JAR_FILE=app/escape-admin.jar

# Add the application's jar to the container
ADD ${JAR_FILE} escape-admin.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/escape-admin.jar"]
=======
FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
>>>>>>> Stashed changes
=======
FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
>>>>>>> Stashed changes
