package com.assignment.cabservice.repository;

import com.assignment.cabservice.constant.RideType;
import com.assignment.cabservice.helper.RiderRequest;
import com.assignment.cabservice.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findBySourceAndDestinationAndAvailableSeatsGreaterThan(String source, String destination, int availableSeats);

    Ride findByRiderId(String riderId);

//    List<Ride> findByRideType(RideType rideType);
}
