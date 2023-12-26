package com.assignment.cabservice.controller;

import com.assignment.cabservice.dto.DriverDTO;
import com.assignment.cabservice.response.SuccessResponse;
import com.assignment.cabservice.service.DriverService;
import com.assignment.cabservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final RideService rideService;
    @PostMapping
    public ResponseEntity<SuccessResponse> createDriver(@NotNull  @RequestBody @Valid DriverDTO driverDTO){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.CREATED.toString())
                        .message("Driver created successfully")
                        .data(driverService.createDriver(driverDTO)).build());
    }

    @GetMapping("fuel/{driverId}")
    public ResponseEntity<SuccessResponse> getFuelSavedFromAllRides(@PathVariable String driverId){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
                        .message("Total Fuel Saved")
                        .data(rideService.getFuelSavedFromAllRides(driverId)).build());
    }

    @GetMapping("{driverId}")
    public ResponseEntity<SuccessResponse> allRidesByDriver(@PathVariable String driverId){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
                        .message("List of Rides from Driver")
                        .data(rideService.allRidesByDriver(driverId)).build());
    }

}