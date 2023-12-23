package com.assignment.cabservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDTO {
    private String id;
    private String riderId;
    @NotBlank
    private String driverId;
    @NotBlank
    private String source;
    @NotBlank
    private String destination;
    private LocalDateTime startTime;
    @Positive
    private int rideTime; // Duration of the ride
    @Positive
    private int availableSeats;
}
