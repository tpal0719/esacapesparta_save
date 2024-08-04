# OpenJDK 11 이미지를 베이스 이미지로 사용
FROM openjdk:11-jre-slim

# 빌드된 JAR 파일을 /app 디렉토리로 복사
COPY build/libs/escape-consumer*.jar /app/escape-consumer.jar

# 어플리케이션 포트 설정 (예: 8080)
EXPOSE 8082

# 어플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/escape-consumer.jar"]