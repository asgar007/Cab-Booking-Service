package com.assignment.cabservice.controller;

import com.assignment.cabservice.dto.DriverDTO;
import com.assignment.cabservice.response.SuccessResponse;
import com.assignment.cabservice.service.DriverService;
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
    @PostMapping
    public ResponseEntity<SuccessResponse> createDriver(@NotNull  @RequestBody @Valid DriverDTO driverDTO){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.CREATED.toString())
                        .message("Driver created successfully")
                        .data(driverService.createDriver(driverDTO)).build());
    }


}