package com.finserv.webhooktask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for Webhook Task Automation
 * 
 * This application automatically executes webhook flows on startup without
 * requiring any REST controllers or user interaction.
 */
@SpringBootApplication
public class WebhookTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookTaskApplication.class, args);
    }
}
