# Webhook Task Application - Project Summary

## Project Overview
This Spring Boot application automatically executes a webhook flow on application startup without requiring any REST controllers or user interaction. The application is designed to fulfill the requirements for a financial services hiring task.

## Key Features
✅ **Automatic Startup Execution**: Runs the entire webhook flow on application startup  
✅ **No REST Controllers**: Pure command-line application that doesn't expose any endpoints  
✅ **Comprehensive HTTP Client Support**: Uses both RestTemplate and WebClient  
✅ **Robust Error Handling**: Detailed error handling with meaningful error messages  
✅ **Production-Ready Logging**: Structured logging with appropriate log levels  
✅ **Clean Architecture**: Well-structured code with separation of concerns  
✅ **Comprehensive Testing**: Unit tests with proper test configuration  
✅ **Fat JAR Build**: Single executable JAR file for easy deployment  

## Technical Implementation

### Registration Details
- **Name**: John Doe  
- **Registration Number**: REG1613 (ends in 13 - odd number, triggers Question 1)  
- **Email**: john@example.com  

### SQL Query (Question 1)
Based on the registration number ending in 13 (odd), the application generates this SQL query:

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

### Workflow Steps
1. **Registration**: POST to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
2. **Response Processing**: Extract webhook URL and access token from JSON response
3. **Query Generation**: Generate appropriate SQL query based on registration number
4. **Final Submission**: POST the query to webhook URL with Bearer token authorization

## Project Structure
```
webhook-task/
├── src/
│   ├── main/
│   │   ├── java/com/finserv/webhooktask/
│   │   │   ├── WebhookTaskApplication.java          # Main application class
│   │   │   ├── config/
│   │   │   │   └── HttpClientConfig.java            # HTTP client beans
│   │   │   ├── dto/                                 # Data Transfer Objects
│   │   │   │   ├── RegistrationRequest.java
│   │   │   │   ├── RegistrationResponse.java
│   │   │   │   └── QueryRequest.java
│   │   │   ├── service/                             # Business logic
│   │   │   │   ├── WebhookService.java              # HTTP operations
│   │   │   │   └── QueryGeneratorService.java       # SQL query generation
│   │   │   └── runner/
│   │   │       └── WebhookTaskRunner.java           # Startup execution logic
│   │   └── resources/
│   │       └── application.properties               # Configuration
│   └── test/
│       └── java/com/finserv/webhooktask/           # Unit tests
├── target/
│   └── webhook-task-1.0.0.jar                      # Executable JAR (27.6 MB)
├── pom.xml                                          # Maven configuration
├── README.md                                        # Documentation
├── build.sh                                         # Build script
└── run.sh                                           # Run script
```

## Build and Execution

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Quick Start
```bash
# Option 1: Use the provided scripts
./build.sh           # Build and test
./run.sh             # Build and run

# Option 2: Manual Maven commands
mvn clean package    # Build with tests
java -jar target/webhook-task-1.0.0.jar    # Run the application

# Option 3: Run with Maven
mvn spring-boot:run  # Run directly with Maven
```

## Key Technologies
- **Spring Boot 3.2.0**: Application framework
- **Spring Web**: HTTP client support (RestTemplate)
- **Spring WebFlux**: Reactive HTTP client (WebClient)
- **Jackson**: JSON processing
- **JUnit 5**: Testing framework
- **Maven**: Build tool
- **SLF4J**: Logging

## Configuration
The application uses a configuration property to control the webhook runner:
- `webhook.runner.enabled=true` (default) - Enables the webhook flow
- `webhook.runner.enabled=false` - Disables the webhook flow (used in tests)

## Test Configuration
Tests are configured to disable the webhook runner using `@TestPropertySource` with `webhook.runner.enabled=false`, ensuring tests don't trigger actual HTTP calls during testing.

## Error Handling
The application includes comprehensive error handling for:
- Network connectivity issues
- Invalid HTTP responses
- Authentication failures (401 Unauthorized)
- Malformed JSON responses
- Missing or invalid webhook URLs/tokens

## Logging
Structured logging with different levels:
- **INFO**: General application flow
- **DEBUG**: Detailed HTTP request/response information
- **ERROR**: Error conditions with full stack traces

## Production Considerations
- Application fails fast if the webhook flow fails (prevents silent failures)
- Comprehensive logging for debugging and monitoring
- Clean shutdown handling
- Proper HTTP timeout configurations
- Bearer token security for API authentication

## File Sizes
- **Source Code**: ~50 KB (8 Java files + configuration)
- **Executable JAR**: 27.6 MB (includes all dependencies)
- **Original JAR**: 13.8 KB (application code only)

## Successful Build Output
```
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
[INFO] Total time: 16.743 s
```

The application is ready for deployment and execution!
