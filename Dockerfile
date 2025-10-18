# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy your Java file into the container
COPY ProfileServer.java /app

# Compile the Java file
RUN javac ProfileServer.java

# Expose port 8080 (Railway uses PORT environment variable)
EXPOSE 8080

# Run your Java program
CMD ["java", "ProfileServer"]
