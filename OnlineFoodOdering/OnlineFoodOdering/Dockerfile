# Use Maven for building the application with JDK 17
FROM maven:3.8.1-openjdk-17-slim AS build

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package

FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file to the production image from the Maven image stage.
COPY --from=build /app/target/OnlineFoodOrdering-0.0.1-SNAPSHOT.jar /app/OnlineFoodOrdering-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8082

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/OnlineFoodOrdering-0.0.1-SNAPSHOT.jar"]
