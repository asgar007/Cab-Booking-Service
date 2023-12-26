package com.assignment.cabservice.dto;

import com.assignment.cabservice.constant.RideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDTO {

    private String riderId;

    @NotBlank(message = "Driver ID is required")
    private String driverId;

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Destination is required")
    private String destination;

    @Positive(message = "Estimated ride time must be a positive integer")
    private int estimatedRideTime;

    @Positive(message = "Fare must be a positive number")
    private double fare;

    @Positive(message = "Available seats must be a positive integer")
    private int availableSeats;

    private RideType rideType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Long version;
}
