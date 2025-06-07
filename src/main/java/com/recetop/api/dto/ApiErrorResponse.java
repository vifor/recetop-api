package com.recetop.api.dto; 

public class ApiErrorResponse {

    private int statusCode;
    private String message;
    private long timestamp;

    // Constructors, Getters, and Setters
    public ApiErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters...
    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public long getTimestamp() { return timestamp; }
}