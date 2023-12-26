package com.assignment.cabservice.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverAddRequest {
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String licenseNumber;
    private String vehicleId;
    private int rating;
    private String status;
}
