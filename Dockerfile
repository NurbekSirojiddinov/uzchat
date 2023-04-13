FROM openjdk:17-jdk-slim-buster
WORKDIR /app

VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENV DB_URL=jdbc:postgresql://uzchatdb:5432/uzchatdb
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=secret
ENTRYPOINT ["java","-jar","/uzchat.jar"]
