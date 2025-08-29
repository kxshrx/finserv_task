package com.finserv.webhooktask;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Basic Spring Boot application context test
 */
@SpringBootTest
@TestPropertySource(properties = {
    "spring.main.web-application-type=none", // Disable web server for tests
    "webhook.runner.enabled=false" // Disable the webhook runner for tests
})
class WebhookTaskApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
    }
}
