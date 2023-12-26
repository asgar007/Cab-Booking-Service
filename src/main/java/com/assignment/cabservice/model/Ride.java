package com.assignment.cabservice.model;

import com.assignment.cabservice.constant.RideType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "rides")
public class Ride {
    @Id
    private String id;

    @DBRef
    private Rider rider;

    @DBRef
    private Driver driver;

    @Indexed
    private String source;

    @Indexed
    private String destination;

    private int estimatedRideTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double fare;

    private RideType rideType;

    private int availableSeats;

    @Version
    private Long version;
}
