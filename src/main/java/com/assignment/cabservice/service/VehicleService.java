package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.VehicleDTO;

public interface VehicleService {
    VehicleDTO createVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicleById(String id);
}
