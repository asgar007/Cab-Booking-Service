package com.assignment.cabservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "vehicles")
public class Vehicle {
    @Id
    private String vehicleId;
    private String userId; // provided later when user/driver own vehicle
    private String make;
    private String model;
    private int year;
    private String fuelType;
    private String transmission;
    private String vin; //Vehicle Identification Number
    private String color;
    private int capacity; // Number of seats
}
