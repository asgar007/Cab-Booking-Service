package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.VehicleDTO;
import com.assignment.cabservice.model.Vehicle;
import com.assignment.cabservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;
    @Override
    public VehicleDTO createVehicle(VehicleDTO vehicleDTO) {
        log.info("vehicleDTO: {}",vehicleDTO);
        return modelMapper.map(vehicleRepository.save(modelMapper.map(vehicleDTO, Vehicle.class)), VehicleDTO.class);
    }

    @Override
    public VehicleDTO getVehicleById(String id) {
        return modelMapper.map(vehicleRepository.findById(id).orElseGet(Vehicle::new), VehicleDTO.class);
    }
}
