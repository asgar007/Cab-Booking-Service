package com.assignment.cabservice.exception;

public class DriverAlreadyRegisteredException extends RuntimeException{
    public DriverAlreadyRegisteredException(String message) {
        super(message);
    }
}
