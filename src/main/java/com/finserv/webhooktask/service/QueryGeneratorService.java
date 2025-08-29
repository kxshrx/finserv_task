package com.finserv.webhooktask.service;

import org.springframework.stereotype.Service;

/**
 * Service class for generating SQL queries based on requirements
 */
@Service
public class QueryGeneratorService {
    
    /**
     * Generates the SQL query for Question 1 based on the registration number ending in 13 (odd)
     * 
     * Task: Find the highest salary credited to an employee, excluding payments made on the 1st day of any month.
     * Return the highest salary, the employee's full name (first name + space + last name), 
     * employee's age (calculated from DOB), and their department name.
     * 
     * Tables:
     * - DEPARTMENT (DEPARTMENT_ID, DEPARTMENT_NAME)
     * - EMPLOYEE (EMP_ID, FIRST_NAME, LAST_NAME, DOB, GENDER, DEPARTMENT)
     * - PAYMENTS (PAYMENT_ID, EMP_ID, AMOUNT, PAYMENT_TIME)
     * 
     * @return The SQL query as a string
     */
    public String generateQuery() {
        return """
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
                """.trim();
    }
    
    /**
     * Gets a formatted and cleaned version of the query
     * 
     * @return Single-line SQL query string
     */
    public String getFormattedQuery() {
        return generateQuery().replaceAll("\\s+", " ");
    }
}
