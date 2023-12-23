package com.assignment.cabservice.repository;

import com.assignment.cabservice.constant.RideType;
import com.assignment.cabservice.dto.RideHistoryDTO;
import com.assignment.cabservice.model.RideHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RideHistoryRepository extends MongoRepository<RideHistory, String> {
    Optional<RideHistory> findByUserId(String driverId);

    List<RideHistory> findByRideType(RideType rideType);
}
