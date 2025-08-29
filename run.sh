#!/bin/bash

# Webhook Task Application Build and Run Script

echo "=== Webhook Task Application Build and Run Script ==="
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "Error: Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed or not in PATH"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

echo "Java version: $(java -version 2>&1 | head -n 1)"
echo "Maven version: $(mvn -version | head -n 1)"
echo

# Clean and build the project
echo "Building the application..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo
    echo "Running the application..."
    echo "=== Application Output ==="
    java -jar target/webhook-task-1.0.0.jar
else
    echo "Build failed!"
    exit 1
fi
