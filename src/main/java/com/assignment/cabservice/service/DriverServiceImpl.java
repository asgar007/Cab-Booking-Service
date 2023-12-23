package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.DriverDTO;
import com.assignment.cabservice.model.Driver;
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

    @Override
    public DriverDTO createDriver(DriverDTO driverDTO) {
        Driver driver = new Driver();
        BeanUtils.copyProperties(driverDTO,driver);
//        log.info("rider : {}",driver);
        return modelMapper.map(driverRepository.save(driver), DriverDTO.class);
    }

    @Override
    public DriverDTO isDriverPresent(String driverId) {
        return modelMapper.map(driverRepository.findById(driverId).orElse(new Driver()), DriverDTO.class);
    }
}
