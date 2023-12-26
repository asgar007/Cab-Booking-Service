package com.assignment.cabservice.repository;

import com.assignment.cabservice.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findBySourceAndDestinationAndAvailableSeatsGreaterThan(String source, String destination, int availableSeats);
    List<Ride> findByDriver_userId(String driverId);
    List<Ride> findByRider_userId(String riderId);
}
