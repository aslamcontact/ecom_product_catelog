FROM eclipse-temurin:20-jre-alpine
WORKDIR /api
COPY ./target/*SNAPSHOT.jar api.jar
ENTRYPOINT ["java","-jar","api.jar"]