package com.finserv.webhooktask.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for QueryGeneratorService
 */
@SpringBootTest
@TestPropertySource(properties = {
    "webhook.runner.enabled=false" // Disable the webhook runner for tests
})
class QueryGeneratorServiceTest {

    @Autowired
    private QueryGeneratorService queryGeneratorService;

    @Test
    void testGenerateQuery() {
        String query = queryGeneratorService.generateQuery();
        
        assertNotNull(query);
        assertTrue(query.contains("SELECT"));
        assertTrue(query.contains("PAYMENTS"));
        assertTrue(query.contains("EMPLOYEE"));
        assertTrue(query.contains("DEPARTMENT"));
        assertTrue(query.contains("DAY(p.PAYMENT_TIME) != 1"));
        assertTrue(query.contains("ORDER BY p.AMOUNT DESC"));
        assertTrue(query.contains("LIMIT 1"));
    }

    @Test
    void testGetFormattedQuery() {
        String formattedQuery = queryGeneratorService.getFormattedQuery();
        
        assertNotNull(formattedQuery);
        assertFalse(formattedQuery.contains("\n"));
        assertTrue(formattedQuery.contains("SELECT"));
    }
}
