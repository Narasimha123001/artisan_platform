FROM ubuntu:latest
LABEL authors="naras"
RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
FROM openjdk:21-jdk-slim

# Set work directory
WORKDIR /app

# Copy jar file to container
COPY /src/target/artisanPlatform-0.0.1-SNAPSHOT.jar app.jar

# Expose port (optional, for local dev)
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]