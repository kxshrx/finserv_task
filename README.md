# Webhook Task Application

This Spring Boot application automatically executes a webhook flow on startup without requiring any REST controllers or user interaction.

## Overview

The application performs the following steps automatically on startup:

1. **Registration**: Sends a POST request to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA` with user details
2. **Query Generation**: Based on registration number REG1613 (ending in 13 - odd), generates SQL query for Question 1
3. **Webhook Submission**: Sends the generated SQL query to the received webhook URL with JWT authorization

## SQL Query (Question 1)

The application generates this SQL query:
```sql
SELECT 
    p.AMOUNT as highest_salary,
    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) as full_name,
    TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) as age,
    d.DEPARTMENT_NAME
FROM PAYMENTS p
INNER JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID
INNER JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID
WHERE DAY(p.PAYMENT_TIME) != 1
ORDER BY p.AMOUNT DESC
LIMIT 1
```

This query finds the highest salary credited to an employee, excluding payments made on the 1st day of any month, and returns:
- Highest salary amount
- Employee's full name (first name + space + last name)
- Employee's age (calculated from DOB)
- Department name

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the Application
```bash
mvn clean package
```

### Run the Application
```bash
java -jar target/webhook-task-1.0.0.jar
```

Or using Maven:
```bash
mvn spring-boot:run
```

## Project Structure

```
src/
├── main/
│   ├── java/com/finserv/webhooktask/
│   │   ├── WebhookTaskApplication.java      # Main application class
│   │   ├── config/
│   │   │   └── HttpClientConfig.java        # HTTP client configuration
│   │   ├── dto/                            # Data Transfer Objects
│   │   │   ├── RegistrationRequest.java
│   │   │   ├── RegistrationResponse.java
│   │   │   └── QueryRequest.java
│   │   ├── service/                        # Business logic services
│   │   │   ├── WebhookService.java
│   │   │   └── QueryGeneratorService.java
│   │   └── runner/
│   │       └── WebhookTaskRunner.java      # Startup execution logic
│   └── resources/
│       └── application.properties          # Application configuration
└── pom.xml                                # Maven configuration
```

## Features

- **Automatic Execution**: Runs the entire flow on application startup
- **Comprehensive Logging**: Detailed logging with proper log levels
- **Error Handling**: Robust error handling with meaningful error messages
- **Clean Architecture**: Well-structured code with separation of concerns
- **Production Ready**: Includes proper configuration and best practices
- **Multiple HTTP Clients**: Uses both RestTemplate and WebClient
- **JWT Authorization**: Properly handles Bearer token authentication

## Logging

The application uses structured logging with different levels:
- `INFO`: General flow information
- `DEBUG`: Detailed request/response information
- `ERROR`: Error conditions with stack traces

## Configuration

The application can be configured through `application.properties`:
- Logging levels and patterns
- HTTP client settings
- Server configuration (if needed)
- Management endpoints

## Error Handling

The application includes comprehensive error handling:
- Network connectivity issues
- Invalid responses
- Authentication failures
- Malformed JSON
- HTTP status code validation

If any step fails, the application will log the error and terminate, ensuring that startup failures are properly reported.
