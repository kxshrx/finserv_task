# Bajaj Finserv Health | Qualifier 1 | JAVA

Spring Boot application implementing the complete Bajaj Finserv Health hiring assessment workflow.

## Overview

This application automatically executes the assessment requirements on startup:

1. **Generate Webhook**: POST to `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
2. **Solve SQL Problem**: Generates appropriate SQL query based on registration number
3. **Submit Solution**: POST to webhook URL with JWT authorization

**Registration Details:**
- Name: J Kishore Kumar   
- Registration Number: 22BCE1613 (ends in 13 → odd → Question 1)
- Email: kishore@example.com

## SQL Solution (Question 1)

Finds the highest salary excluding payments on the 1st day of any month:

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

## Quick Start

### Download & Run
```bash
wget https://github.com/kxshrx/finserv_task/raw/main/releases/webhook-task-1.0.0.jar
java -jar webhook-task-1.0.0.jar
```

### Build from Source
```bash
git clone https://github.com/kxshrx/finserv_task.git
cd finserv_task
mvn clean package
java -jar target/webhook-task-1.0.0.jar
```

## Technical Implementation

- **Framework**: Spring Boot 3.2.0
- **HTTP Clients**: RestTemplate & WebClient
- **Startup Execution**: CommandLineRunner (no controllers)
- **Authorization**: JWT Bearer token
- **Java Version**: 17+

## Project Structure

```
src/main/java/com/finserv/webhooktask/
├── WebhookTaskApplication.java          # Main application
├── config/HttpClientConfig.java         # HTTP client beans
├── dto/                                 # Request/Response objects
├── service/                             # Business logic
└── runner/WebhookTaskRunner.java        # Startup execution
```

## Assessment Compliance

✅ **Spring Boot with RestTemplate/WebClient**  
✅ **No controllers - CommandLineRunner startup**  
✅ **JWT Authorization header implementation**  
✅ **Complete workflow automation**  
✅ **Question 1 handling (odd registration)**  

## Submission

- **Repository**: https://github.com/kxshrx/finserv_task
- **Downloadable JAR**: https://github.com/kxshrx/finserv_task/raw/main/releases/webhook-task-1.0.0.jar
- **Assessment Form**: https://forms.office.com/r/5Kzb1h7fre

Built for Bajaj Finserv Health Technical Assessment
