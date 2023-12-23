package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.RideDTO;
import com.assignment.cabservice.helper.FuelRequest;
import com.assignment.cabservice.helper.RideTypeRequest;
import com.assignment.cabservice.helper.RiderRequest;

import java.util.List;

public interface RideService {
    RideDTO offerRideByDriver(RideDTO rideDTO);
    List<RideDTO> selectRideByRider(RiderRequest riderRequest);

    //below two are for Testing
    RideDTO bookRide(String riderId, String rideId);
    RideDTO bookRideTest(String riderId, String rideId);

    RideDTO bookRideUpdated(String riderId, String rideId);

    int getFuelConsumed(FuelRequest fuelRequest);

}
