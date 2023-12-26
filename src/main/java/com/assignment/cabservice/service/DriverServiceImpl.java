package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.DriverDTO;
import com.assignment.cabservice.dto.VehicleDTO;
import com.assignment.cabservice.exception.DriverAlreadyRegisteredException;
import com.assignment.cabservice.model.Driver;
import com.assignment.cabservice.model.Vehicle;
import com.assignment.cabservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverServiceImpl implements DriverService{
    private final DriverRepository driverRepository;
    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;


    @Override
    public DriverDTO createDriver(DriverDTO driverDTO) {
        // Check if the driver with the given username already exists
        Driver existingDriver = driverRepository.findByUsername(driverDTO.getUsername());
        log.info("existingDriver {}",existingDriver);

        if (existingDriver != null) {
            throw new DriverAlreadyRegisteredException("Driver with username " + driverDTO.getUsername() + " already registered");
        }

        log.info("Creating new driver: {}", driverDTO);
        Driver driver = new Driver();
        BeanUtils.copyProperties(driverDTO, driver);
        log.info("driver: {}", driver);
        // Check if the vehicleId is provided in the request body
        if (driverDTO.getVehicleId() != null) {
            VehicleDTO vehicleDTO = vehicleService.getVehicleById(driverDTO.getVehicleId());
            driver.setVehicle(modelMapper.map(vehicleDTO, Vehicle.class));
            log.info("Driver with Vehicle {}", driver);
        }
        driver = driverRepository.save(driver);
        BeanUtils.copyProperties(driver,driverDTO);
        return driverDTO;
    }

    @Override
    public DriverDTO isDriverPresent(String driverId) {
        Driver driver = driverRepository.findById(driverId).orElse(new Driver());
        DriverDTO driverDTO = new DriverDTO();
        BeanUtils.copyProperties(driver,driverDTO);
        return driverDTO;
    }
}
