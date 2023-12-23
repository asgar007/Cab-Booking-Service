package com.assignment.cabservice.exception;

public class RideHistoryNotFoundException extends RuntimeException{
    public RideHistoryNotFoundException(String message) {
        super(message);
    }
}
