#!/bin/bash

# Quick build and test script

echo "=== Building and Testing Webhook Task Application ==="
echo

# Build the project
echo "1. Compiling and running tests..."
mvn clean test

if [ $? -eq 0 ]; then
    echo "✅ Tests passed!"
    echo
    echo "2. Building the application..."
    mvn package -DskipTests
    
    if [ $? -eq 0 ]; then
        echo "✅ Build successful!"
        echo
        echo "Generated JAR: target/webhook-task-1.0.0.jar"
        echo
        echo "To run the application:"
        echo "  java -jar target/webhook-task-1.0.0.jar"
        echo "  OR"
        echo "  ./run.sh"
    else
        echo "❌ Build failed!"
        exit 1
    fi
else
    echo "❌ Tests failed!"
    exit 1
fi
