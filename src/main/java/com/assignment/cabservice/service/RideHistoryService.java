package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.RideHistoryDTO;
import com.assignment.cabservice.helper.RideTypeRequest;

import java.util.List;

public interface RideHistoryService {
    RideHistoryDTO createHistory(RideHistoryDTO rideHistoryDTO);

    RideHistoryDTO findRideHistoryByDriverId(String driverId);
    List<RideHistoryDTO> getRidesByRideType(RideTypeRequest rideTypeRequest);
}
