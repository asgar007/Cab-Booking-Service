package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.RideDTO;
import com.assignment.cabservice.helper.BookRideRequest;
import com.assignment.cabservice.helper.RiderRequest;
import com.assignment.cabservice.helper.TestRequest;

import java.util.List;

public interface RideService {
    RideDTO offerRideByDriver(RideDTO rideDTO);
    List<RideDTO> selectRideByRider(RiderRequest riderRequest);
//
//    //below two are for Testing
//    RideDTO bookRide(String riderId, String rideId);
//    RideDTO bookRideTest(String riderId, String rideId);
//

    RideDTO bookRide(BookRideRequest bookRideRequest);
    RideDTO bookRideUpdated(String riderId, String rideId);
    List<RideDTO> allRidesByDriver(String driverId);
    List<RideDTO> allRidesByRider(String riderId);

//
    int getFuelSavedFromAllRides(String driverId);

    RideDTO testConcurrency(TestRequest testRequest); //For Testing Only

}
