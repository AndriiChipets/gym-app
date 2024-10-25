FROM maven:3.9.8-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean install -D skipTests

FROM openjdk:21

COPY --from=build /target/gym-app-1.0-SNAPSHOT.jar gym-app.jar
COPY ./localhost.env localhost.env
ENTRYPOINT ["java","-jar","gym-app.jar"]