package com.assignment.cabservice.controller;

import com.assignment.cabservice.dto.RideDTO;
import com.assignment.cabservice.helper.BookRideRequest;
import com.assignment.cabservice.helper.RiderRequest;
import com.assignment.cabservice.helper.TestRequest;
import com.assignment.cabservice.response.SuccessResponse;
import com.assignment.cabservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;



    @PostMapping
    public ResponseEntity<SuccessResponse> offerRideByDriver(@RequestBody @Valid RideDTO rideDTO){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.CREATED.toString())
                        .message("Ride offered successfully!")
                        .data(rideService.offerRideByDriver(rideDTO)).build());
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> selectRideByRider(@RequestBody RiderRequest riderRequest){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
                        .message("List of Rides !")
                        .data(rideService.selectRideByRider(riderRequest)).build());
    }

    @PutMapping("book")
    public ResponseEntity<SuccessResponse> bookRideByRider(@RequestBody BookRideRequest bookRideRequest){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
                        .message("Ride booked Successfully !")
                        .data(rideService.bookRide(bookRideRequest)).build());
    }

    @PutMapping("test")
    public ResponseEntity<SuccessResponse> testConcurrency(@RequestBody TestRequest testRequest){
        return  ResponseEntity.ok()
                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
                        .message("Concurrency !")
                        .data(rideService.testConcurrency(testRequest)).build());
    }

    //api 5

//    @GetMapping("type")
//    public ResponseEntity<SuccessResponse> getRidesByRideType(@RequestBody RideTypeRequest rideTypeRequest){
//        return  ResponseEntity.ok()
//                .body(SuccessResponse.builder().error(false).statusCode(HttpStatus.OK.toString())
//                        .message("List of Rides by RideType!")
//                        .data(rideHistoryService.getRidesByRideType(rideTypeRequest)).build());
//    }

}
