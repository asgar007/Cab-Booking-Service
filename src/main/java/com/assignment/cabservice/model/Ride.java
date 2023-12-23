package com.assignment.cabservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "rides")
public class Ride {
    @Id
    private String id;
    private String riderId;
    private String driverId;
    @Indexed
    private String source;
    @Indexed
    private String destination;
    private LocalDateTime startTime;
    @Indexed
    private int rideTime; // Duration of the ride
    private int availableSeats;
}
