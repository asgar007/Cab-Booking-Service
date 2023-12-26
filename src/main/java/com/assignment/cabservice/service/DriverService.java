package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.DriverDTO;

public interface DriverService {
//    DriverDTO createDriver(DriverAddRequest driverDTO);
    DriverDTO createDriver(DriverDTO driverDTO);
    DriverDTO isDriverPresent(String driverId);


}
