package com.finserv.webhooktask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for the registration response containing webhook URL and access token
 */
public class RegistrationResponse {
    
    @JsonProperty("webhook")
    private String webhook;
    
    @JsonProperty("accessToken")
    private String accessToken;
    
    public RegistrationResponse() {}
    
    public RegistrationResponse(String webhook, String accessToken) {
        this.webhook = webhook;
        this.accessToken = accessToken;
    }
    
    // Getters and Setters
    public String getWebhook() {
        return webhook;
    }
    
    public void setWebhook(String webhook) {
        this.webhook = webhook;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    @Override
    public String toString() {
        return "RegistrationResponse{" +
                "webhook='" + webhook + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
