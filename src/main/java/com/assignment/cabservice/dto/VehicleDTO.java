package com.assignment.cabservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {
    private String vehicleId;

    private String userId; // provided later when user/driver own vehicle

    @NotBlank(message = "Make cannot be blank")
    private String make;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @Positive(message = "Year must be a positive number")
    private int year;

    @NotBlank(message = "Fuel type cannot be blank")
    private String fuelType;

    @NotBlank(message = "Transmission cannot be blank")
    private String transmission;

    @NotBlank(message = "VIN cannot be blank")
    private String vin;

    @NotBlank(message = "Color cannot be blank")
    private String color;

    @Positive(message = "Capacity must be a positive number")
    private int capacity;
}
