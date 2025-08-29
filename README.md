# Bajaj Finserv Health | Qualifier 1 | JAVA - Spring Boot Webhook Application

This Spring Boot application implements the complete assessment workflow as specified in the Bajaj Finserv Health hiring challenge.

## Task Implementation

The application automatically executes the following workflow on startup without any REST controllers or user interaction:

1. **Generate Webhook**: Sends POST request to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
2. **Solve SQL Problem**: Based on registration number ending (odd/even), generates the appropriate SQL query
3. **Submit Solution**: Sends the final SQL query to the webhook URL using JWT token authorization

## Registration Details

- **Name**: John Doe
- **Registration Number**: REG1613 (ending in 13 - odd → Question 1)
- **Email**: john@example.com

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

## Quick Start

### Download and Run (Recommended)
```bash
# Download the JAR directly
wget https://github.com/kxshrx/finserv_task/raw/main/releases/webhook-task-1.0.0.jar

# Run the application
java -jar webhook-task-1.0.0.jar
```

### Build from Source
```bash
# Clone the repository
git clone https://github.com/kxshrx/finserv_task.git
cd finserv_task

# Build the application
mvn clean package

# Run the application
java -jar target/webhook-task-1.0.0.jar
```

## Assessment Compliance

✅ **Spring Boot Application**: Built with Spring Boot 3.2.0  
✅ **Automatic Startup**: Runs without controllers using CommandLineRunner  
✅ **RestTemplate/WebClient**: Uses both for HTTP requests  
✅ **JWT Authorization**: Implements Bearer token authentication  
✅ **Public GitHub Repository**: Available at https://github.com/kxshrx/finserv_task  
✅ **Downloadable JAR**: https://github.com/kxshrx/finserv_task/raw/main/releases/webhook-task-1.0.0.jar

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

## Submission Checklist

### ✅ Required Components

1. **Public GitHub Repository**: https://github.com/kxshrx/finserv_task
2. **Source Code**: Complete Spring Boot application with all required files
3. **Final JAR Output**: Available in releases directory
4. **RAW Downloadable JAR Link**: https://github.com/kxshrx/finserv_task/raw/main/releases/webhook-task-1.0.0.jar

### 📝 Assessment Requirements Met

- ✅ Spring Boot application with RestTemplate/WebClient
- ✅ No controllers - uses CommandLineRunner for startup execution
- ✅ JWT token in Authorization header
- ✅ Complete workflow: Generate Webhook → Solve SQL → Submit Solution
- ✅ Handles odd registration number (Question 1)
- ✅ Production-ready JAR file

### 🔗 Submit Here
**Assessment Form**: https://forms.office.com/r/5Kzb1h7fre

**Repository Format**: `https://github.com/kxshrx/finserv_task.git`
