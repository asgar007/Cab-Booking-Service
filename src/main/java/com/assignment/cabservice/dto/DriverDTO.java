package com.assignment.cabservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDTO {
    private String id;
    @NotBlank
    private String driverName;
    @NotBlank
    private String phoneNumber;
    private String currentLocation;

    @NotBlank
    private String licenseNumber;
    private String vehicle;
    private List<String> rideHistoryId; //rideId will be history
}