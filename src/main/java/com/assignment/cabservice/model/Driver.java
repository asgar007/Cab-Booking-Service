package com.assignment.cabservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "drivers")
public class Driver{
    @Id
    private String id;
    @Indexed(unique = true)
    private String driverName;
    private String phoneNumber;
    private String currentLocation;
    private String licenseNumber;
    private String vehicle;
    private List<String> rideHistoryId; //rideId will be history
}