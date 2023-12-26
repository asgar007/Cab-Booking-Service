package com.assignment.cabservice.controller;

import com.assignment.cabservice.dto.RiderDTO;
import com.assignment.cabservice.response.SuccessResponse;
import com.assignment.cabservice.service.RideService;
import com.assignment.cabservice.service.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/riders")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final RideService rideService;
    @PostMapping
    public ResponseEntity<SuccessResponse> createRider(@NotNull @RequestBody @Valid RiderDTO riderDTO){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.CREATED.toString())
                        .message("Rider created successfully")
                        .data(riderService.createRider(riderDTO)).build());
    }

    @GetMapping("{riderId}")
    public ResponseEntity<SuccessResponse> allRidesByRider(@PathVariable String riderId){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
                        .message("List of Rides by Rider")
                        .data(rideService.allRidesByRider(riderId)).build());
    }


}
