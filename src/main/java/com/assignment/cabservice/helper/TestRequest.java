package com.assignment.cabservice.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
    private String firstRiderId;
    private String secondRiderId;
    private String rideId;
    private Long version;
}
