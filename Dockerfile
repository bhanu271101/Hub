# Use a lightweight OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the Spring Boot fat JAR into the container and rename it to app.jar
COPY target/orders-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app listens on (default 8080)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
