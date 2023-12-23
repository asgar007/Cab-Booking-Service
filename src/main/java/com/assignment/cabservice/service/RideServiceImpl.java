package com.assignment.cabservice.service;

import com.assignment.cabservice.constant.RideType;
import com.assignment.cabservice.dto.DriverDTO;
import com.assignment.cabservice.dto.RideDTO;
import com.assignment.cabservice.dto.RideHistoryDTO;
import com.assignment.cabservice.exception.DriverNotFoundException;
import com.assignment.cabservice.exception.RideAlreadyBookedException;
import com.assignment.cabservice.exception.RideHistoryNotFoundException;
import com.assignment.cabservice.exception.RideNotFoundException;
import com.assignment.cabservice.helper.FuelRequest;
import com.assignment.cabservice.helper.RiderRequest;
import com.assignment.cabservice.model.Ride;
import com.assignment.cabservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideServiceImpl implements RideService{

    private final RideRepository rideRepository;
    private final ModelMapper modelMapper;
    private final DriverService driverService;
    private final RideHistoryService rideHistoryService;
    @Override
    public RideDTO offerRideByDriver(RideDTO rideDTO) {
        // validate the driverId
        DriverDTO driverPresent = driverService.isDriverPresent(rideDTO.getDriverId());

        if (driverPresent == null || driverPresent.getId() == null) {
            throw new DriverNotFoundException("Driver not found with ID: " + rideDTO.getDriverId());
        }

        Ride ride = modelMapper.map(rideDTO, Ride.class);

        ride.setStartTime(LocalDateTime.now());
        Ride savedRide = rideRepository.save(ride);

        //save ride as OFFERED till now later you can update to COMPLETED if so
        RideHistoryDTO rideHistoryDTO = RideHistoryDTO.builder()
                .userId(rideDTO.getDriverId())
                .rideIds(new ArrayList<>())
                .rideType(RideType.OFFERED).build();
        rideHistoryDTO.getRideIds().add(ride.getId());
        log.info("ride id{}", ride.getId());

        rideHistoryService.createHistory(rideHistoryDTO);

        return modelMapper.map(savedRide, RideDTO.class);
    }

    @Override
    public List<RideDTO> selectRideByRider(RiderRequest riderRequest) {
        log.info("riderRequest {},", riderRequest);

        // Find rides based on source and destination
        List<Ride> matchingRides = rideRepository.findBySourceAndDestinationAndAvailableSeatsGreaterThan(
                riderRequest.getSource(), riderRequest.getDestination(), 0);

//        log.info("matchingRides {},", matchingRides);

        if (matchingRides.isEmpty()) {
            log.info("No matching rides found for the given criteria.");
            return Collections.emptyList();
        }
        List<Ride> availableRides = matchingRides.stream()
                .filter(r -> r.getAvailableSeats() > 0)
                .collect(Collectors.toList());

//        log.info("availableRides {}", availableRides);
        if (availableRides.isEmpty()) {
            log.info("No rides with available seats found.");
            return Collections.emptyList();
        }
        List<Ride> sortedRides;
        switch (riderRequest.getStrategy().toLowerCase()) {
            case "earliest":
                sortedRides = availableRides.stream()
                        .sorted(Comparator.comparing(Ride::getStartTime))
                        .collect(Collectors.toList());
                break;
            case "shortest":
                sortedRides = availableRides.stream()
                        .sorted(Comparator.comparingInt(Ride::getRideTime))
                        .collect(Collectors.toList());
                break;
            default:
                // Invalid strategy, handle exception or return an empty list
                log.error("Invalid strategy: {}", riderRequest.getStrategy());
                return Collections.emptyList();
        }
        log.info("sortedRides {}", sortedRides);

        return sortedRides.stream()
                .map(r -> modelMapper.map(r, RideDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RideDTO bookRide(String riderId, String rideId) {
        log.info("riderId {}, rideId{}", riderId, rideId);
        Ride ride = rideRepository.findById(rideId).orElse(new Ride());
        ride.setRiderId(riderId);
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        rideRepository.save(ride);
        RideHistoryDTO rideHistoryDTO = rideHistoryService.findRideHistoryByDriverId(ride.getDriverId());
        if (rideHistoryDTO == null || rideHistoryDTO.getRideIds().isEmpty()) {
            throw new RideHistoryNotFoundException("Ride history not found or is empty");
        }
        rideHistoryDTO.setRideType(RideType.COMPLETED);
        log.info("rideHistoryDTO  {}", rideHistoryDTO);
        rideHistoryService.createHistory(rideHistoryDTO);
        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    public RideDTO bookRideUpdated(String riderId, String rideId) {
        log.info("riderId {}, rideId{}", riderId, rideId);

        // Check if riderId is already present
        Ride existingRideForRider = rideRepository.findByRiderId(riderId);
        if (existingRideForRider != null) {
            throw new RideAlreadyBookedException("Ride already booked for rider with ID: " + riderId);
        }

        Ride ride = rideRepository.findById(rideId).orElse(new Ride());
        ride.setRiderId(riderId);
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        rideRepository.save(ride);

        RideHistoryDTO rideHistoryDTO = rideHistoryService.findRideHistoryByDriverId(ride.getDriverId());
        if (rideHistoryDTO == null || rideHistoryDTO.getRideIds().isEmpty()) {
            throw new RideHistoryNotFoundException("Ride history not found or is empty");
        }

        rideHistoryDTO.setRideType(RideType.COMPLETED);
        log.info("rideHistoryDTO  {}", rideHistoryDTO);
        rideHistoryService.createHistory(rideHistoryDTO);

        return modelMapper.map(ride, RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO bookRideTest(String riderId, String rideId) {
        try {
            log.info("riderId {}, rideId{}", riderId, rideId);

            Ride ride = rideRepository.findById(rideId)
                    .orElseThrow(() -> new RideNotFoundException("Ride not found"));

            if (ride.getAvailableSeats() <= 0) {
                throw new RuntimeException("No available seats for the ride");
            }

            ride.setRiderId(riderId);
            ride.setAvailableSeats(ride.getAvailableSeats() - 1);

            rideRepository.save(ride); //locking

            RideHistoryDTO rideHistoryDTO = rideHistoryService.findRideHistoryByDriverId(ride.getDriverId());
            if (rideHistoryDTO == null || rideHistoryDTO.getRideIds().isEmpty()) {
                throw new RideHistoryNotFoundException("Ride history not found or is empty");
            }
            rideHistoryDTO.setRideType(RideType.COMPLETED);
            log.info("rideHistoryDTO  {}", rideHistoryDTO);
            rideHistoryService.createHistory(rideHistoryDTO);

            return modelMapper.map(ride, RideDTO.class);
        } catch (OptimisticLockingFailureException ex) {
            throw new ConcurrencyFailureException("Concurrent update detected. Please try again.");
        } catch (RideNotFoundException | RideHistoryNotFoundException ex) {
            throw new RuntimeException("Failed to book ride: " + ex.getMessage());
        }
    }

    @Override
    public int getFuelConsumed(FuelRequest fuelRequest) {
        Ride ride = rideRepository.findById(fuelRequest.getRideId())
                .orElseThrow(() -> new RideNotFoundException("Ride not found with ID: " + fuelRequest.getRideId()));
        return ride.getRideTime();
    }

}
