# --- Stage 1: Build the application ---
# Use an official Maven image to build the application .jar file
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file first to leverage Docker's layer caching
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Run the Maven package command to build the .jar file
# -DskipTests will skip running the tests during the Docker build for speed
RUN mvn clean package -DskipTests


# --- Stage 2: Create the final, lightweight image ---
# Use a minimal Java runtime image
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy ONLY the .jar file from the 'build' stage into the final image
# This creates a much smaller and more secure final image
COPY --from=build /app/target/app.jar app.jar

# Expose the port that the application runs on
EXPOSE 8080

# The command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]