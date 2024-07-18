FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/OnlineFoodOrdering-0.0.1-SNAPSHOT.jar /app/OnlineFoodOrdering-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/OnlineFoodOrdering-0.0.1-SNAPSHOT.jar"]
