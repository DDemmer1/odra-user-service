FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/odra-user-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar" ,"-Dspring.profiles.active=production","/app.jar"]