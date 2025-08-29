package com.finserv.webhooktask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for HTTP clients and other beans
 */
@Configuration
public class HttpClientConfig {
    
    /**
     * Creates a RestTemplate bean for making HTTP requests
     * 
     * @return RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    /**
     * Creates a WebClient bean for reactive HTTP requests
     * 
     * @return WebClient instance
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }
}
