# Base image for building the application
FROM openjdk:21-slim AS builder
WORKDIR /opt/app
COPY . /opt/app
RUN chmod +x ./mvnw && ./mvnw package -DskipTests

# Runtime image
FROM openjdk:21-slim as final
WORKDIR /opt/app
ARG JAR_FILE=/opt/app/target/xprice-*SNAPSHOT.jar
COPY --from=builder ${JAR_FILE} xprice-app.jar

ENTRYPOINT ["java", "-jar", "xprice-app.jar"]
EXPOSE 8080
