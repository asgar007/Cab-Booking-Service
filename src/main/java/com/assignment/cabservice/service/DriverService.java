package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.DriverDTO;
import com.assignment.cabservice.dto.RideDTO;
import com.assignment.cabservice.helper.RideTypeRequest;

import java.util.List;

public interface DriverService {
    DriverDTO createDriver(DriverDTO driverDTO);
    DriverDTO isDriverPresent(String driverId);


}
