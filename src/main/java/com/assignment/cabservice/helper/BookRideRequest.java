package com.assignment.cabservice.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRideRequest {
    private String riderId;
    private String rideId;
    private Long version;
}
