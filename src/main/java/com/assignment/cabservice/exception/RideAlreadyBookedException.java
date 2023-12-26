package com.assignment.cabservice.exception;

public class RideAlreadyBookedException extends RuntimeException{
    public RideAlreadyBookedException(String message) {
        super(message);
    }
}
