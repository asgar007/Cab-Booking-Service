package com.assignment.cabservice.exception;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(String message) {
        super(message);
    }
}
