package com.assignment.cabservice.controller;

import com.assignment.cabservice.helper.FuelRequest;
import com.assignment.cabservice.response.SuccessResponse;
import com.assignment.cabservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/fuels")
@RequiredArgsConstructor
public class FuelController {

    private final RideService rideService;
    @GetMapping
    public ResponseEntity<SuccessResponse> getFuelConsumed(@NotNull @RequestBody FuelRequest fuelRequest){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
                        .message("Fuel saved Details !")
                        .data(rideService.getFuelConsumed(fuelRequest)).build());
    }
}
