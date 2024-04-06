FROM openjdk:17-oracle
MAINTAINER dauren
COPY app.jar my-spring-backend.jar
ENTRYPOINT ["java", "-jar", "my-spring-backend.jar"]