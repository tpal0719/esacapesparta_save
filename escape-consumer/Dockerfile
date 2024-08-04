<<<<<<< Updated upstream
<<<<<<< Updated upstream
# Base image
FROM openjdk:11-jre-slim

# Add Maintainer Info
LABEL maintainer="tpal071997@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8081 available to the world outside this container
EXPOSE 8082

# The application's jar file
ARG JAR_FILE=app/escape-consumer.jar

# Add the application's jar to the container
ADD ${JAR_FILE} escape-consumer.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/escape-consumer.jar"]
=======
=======
>>>>>>> Stashed changes
# OpenJDK 11 이미지를 베이스 이미지로 사용
FROM openjdk:11-jre-slim

# JAR 파일을 /app 디렉토리로 복사
COPY build/libs/escape-admin*.jar /app/escape-admin.jar

# 어플리케이션 포트 설정 (예: 8080)
EXPOSE 8082

# 어플리케이션 실행
<<<<<<< Updated upstream
ENTRYPOINT ["java", "-jar", "/app/escapeSparta.jar"]
>>>>>>> Stashed changes
=======
ENTRYPOINT ["java", "-jar", "/app/escapeSparta.jar"]
>>>>>>> Stashed changes
