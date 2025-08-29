package com.finserv.webhooktask.service;

import org.springframework.stereotype.Service;

@Service
public class QueryGeneratorService {
    
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
    
    public String getFormattedQuery() {
        return generateQuery().replaceAll("\\s+", " ");
    }
}
