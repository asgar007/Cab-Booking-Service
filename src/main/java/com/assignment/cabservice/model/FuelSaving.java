package com.assignment.cabservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "fuelSavings")
public class FuelSaving {
    @Id
    private String id;
    private String driverId;
    private int hoursSaved; // Hours of ride equals liters of fuel saved
}