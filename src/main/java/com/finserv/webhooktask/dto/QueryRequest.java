package com.finserv.webhooktask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryRequest {
    
    @JsonProperty("finalQuery")
    private String finalQuery;
    
    public QueryRequest() {}
    
    public QueryRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }
    
    public String getFinalQuery() {
        return finalQuery;
    }
    
    public void setFinalQuery(String finalQuery) {
        this.finalQuery = finalQuery;
    }
    
    @Override
    public String toString() {
        return "QueryRequest{" +
                "finalQuery='" + finalQuery + '\'' +
                '}';
    }
}
