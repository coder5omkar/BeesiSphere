# Use a lightweight OpenJDK base image
FROM openjdk:17-jdk-slim

# Maintainer information (MAINTAINER is deprecated; use LABEL instead)
LABEL maintainer="omkarAmale"

# Copy the JAR file into the container
COPY target/todo-management-0.0.1-SNAPSHOT.jar todo-management-0.0.1-SNAPSHOT.jar

# Fixing the ENTRYPOINT syntax
ENTRYPOINT ["java", "-jar", "todo-management-0.0.1-SNAPSHOT.jar"]
