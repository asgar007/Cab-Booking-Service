package com.assignment.cabservice.controller;

import com.assignment.cabservice.dto.VehicleDTO;
import com.assignment.cabservice.response.SuccessResponse;
import com.assignment.cabservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createVehicle(@NotNull @RequestBody @Valid VehicleDTO vehicleDTO){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.CREATED.toString())
                        .message("Vehicle created successfully")
                        .data(vehicleService.createVehicle(vehicleDTO)).build());
    }
}
