package com.assignment.cabservice.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO extends UserDTO {

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @NotBlank(message = "Vehicle ID is Required")
    private String vehicleId;

    @Positive(message = "Rating must be a positive number")
    private double rating;

    @NotBlank(message = "Status cannot be blank")
    private String status; //Offline/Online
}