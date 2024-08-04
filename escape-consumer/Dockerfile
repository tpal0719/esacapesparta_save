# OpenJDK 11 이미지를 베이스 이미지로 사용
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /apps
## Copy the Gradle wrapper and build scripts
#COPY gradlew .
#COPY gradle/ gradle/
#COPY build.gradle .
#COPY settings.gradle .

# Give gradlew file execute permissions
#RUN chmod +x gradlew


# Build the application
#RUN ./gradlew bootJar
COPY ./build/libs/*.jar /apps/escape-consumer.jar

# 어플리케이션 포트 설정 (예: 8080)
EXPOSE 8080

# 어플리케이션 실행
CMD ["java", "-jar", "/apps/escape-consumer.jar"]