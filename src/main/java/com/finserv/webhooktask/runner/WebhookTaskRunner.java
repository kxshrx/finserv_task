package com.finserv.webhooktask.runner;

import com.finserv.webhooktask.dto.QueryRequest;
import com.finserv.webhooktask.dto.RegistrationRequest;
import com.finserv.webhooktask.dto.RegistrationResponse;
import com.finserv.webhooktask.service.QueryGeneratorService;
import com.finserv.webhooktask.service.WebhookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner implementation that executes the entire webhook flow on application startup
 * 
 * This class implements the core business logic:
 * 1. Send registration request to get webhook URL and access token
 * 2. Generate SQL query based on registration number (REG1613 - odd ending, so Question 1)
 * 3. Send the query to the webhook URL with authorization
 */
@Component
public class WebhookTaskRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookTaskRunner.class);
    
    @Value("${webhook.runner.enabled:true}")
    private boolean enabled;
    
    @Autowired
    private WebhookService webhookService;
    
    @Autowired
    private QueryGeneratorService queryGeneratorService;
    
    @Override
    public void run(String... args) throws Exception {
        if (!enabled) {
            logger.info("WebhookTaskRunner is disabled via configuration");
            return;
        }
        
        logger.info("=== Starting Webhook Task Automation ===");
        
        try {
            // Step 1: Register and get webhook details
            logger.info("Step 1: Registering and getting webhook details...");
            RegistrationRequest registrationRequest = new RegistrationRequest(
                "John Doe",
                "REG1613", 
                "john@example.com"
            );
            
            RegistrationResponse registrationResponse = webhookService.registerAndGetWebhook(registrationRequest);
            
            // Validate response
            if (registrationResponse.getWebhook() == null || registrationResponse.getWebhook().isEmpty()) {
                throw new Exception("Invalid webhook URL received");
            }
            if (registrationResponse.getAccessToken() == null || registrationResponse.getAccessToken().isEmpty()) {
                throw new Exception("Invalid access token received");
            }
            
            logger.info("Registration completed successfully");
            
            // Step 2: Generate SQL query
            logger.info("Step 2: Generating SQL query for Question 1 (registration number REG1613 ends in 13 - odd)...");
            String sqlQuery = queryGeneratorService.getFormattedQuery();
            logger.info("Generated SQL query: {}", sqlQuery);
            
            // Step 3: Send query to webhook
            logger.info("Step 3: Sending query to webhook...");
            QueryRequest queryRequest = new QueryRequest(sqlQuery);
            
            String webhookResponse = webhookService.sendQueryToWebhook(
                registrationResponse.getWebhook(),
                registrationResponse.getAccessToken(),
                queryRequest
            );
            
            logger.info("=== Webhook Task Automation Completed Successfully ===");
            logger.info("Final webhook response: {}", webhookResponse);
            
        } catch (Exception e) {
            logger.error("=== Webhook Task Automation Failed ===");
            logger.error("Error: {}", e.getMessage(), e);
            
            // In a production environment, you might want to:
            // - Send alerts to monitoring systems
            // - Retry with exponential backoff
            // - Store failed attempts for manual review
            
            throw e; // Re-throw to ensure the application startup fails if critical flow fails
        }
    }
}
