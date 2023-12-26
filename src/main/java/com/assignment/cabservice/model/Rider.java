package com.assignment.cabservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "riders")
public class Rider extends User {

//    @DBRef
//    private Ride lastRide;
    private int ridesTaken;
}
