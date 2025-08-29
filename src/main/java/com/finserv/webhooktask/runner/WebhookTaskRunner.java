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
            logger.info("Step 1: Registering and getting webhook details...");
            RegistrationRequest registrationRequest = new RegistrationRequest(
                "Kishore Kumar",
                "22BCE1613", 
                "kishoren@example.com"
            );
            
            RegistrationResponse registrationResponse = webhookService.registerAndGetWebhook(registrationRequest);
            
            if (registrationResponse.getWebhook() == null || registrationResponse.getWebhook().isEmpty()) {
                throw new Exception("Invalid webhook URL received");
            }
            if (registrationResponse.getAccessToken() == null || registrationResponse.getAccessToken().isEmpty()) {
                throw new Exception("Invalid access token received");
            }
            
            logger.info("Registration completed successfully");
            
            logger.info("Step 2: Generating SQL query for Question 1 (registration number REG1613 ends in 13 - odd)...");
            String sqlQuery = queryGeneratorService.getFormattedQuery();
            logger.info("Generated SQL query: {}", sqlQuery);
            
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
            throw e;
        }
    }
}
