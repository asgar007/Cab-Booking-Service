package com.assignment.cabservice.service;

import com.assignment.cabservice.constant.RideType;
import com.assignment.cabservice.dto.DriverDTO;
import com.assignment.cabservice.dto.RideDTO;
import com.assignment.cabservice.dto.RiderDTO;
import com.assignment.cabservice.exception.DriverNotFoundException;
import com.assignment.cabservice.exception.RideAlreadyBookedException;
import com.assignment.cabservice.exception.RideNotFoundException;
import com.assignment.cabservice.helper.BookRideRequest;
import com.assignment.cabservice.helper.RiderRequest;
import com.assignment.cabservice.helper.TestRequest;
import com.assignment.cabservice.model.Driver;
import com.assignment.cabservice.model.Ride;
import com.assignment.cabservice.model.Rider;
import com.assignment.cabservice.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;

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
    private final DriverService driverService;
    private final RiderService riderService;

    @Override
    public RideDTO offerRideByDriver(RideDTO rideDTO) {
        log.info("rideDTO: {}", rideDTO);
        // validate the driverId
        DriverDTO driverPresentDTO = driverService.isDriverPresent(rideDTO.getDriverId());

        if (driverPresentDTO == null || driverPresentDTO.getUserId() == null) {
            log.info("Driver not found with ID: {}",rideDTO.getDriverId());
            throw new DriverNotFoundException("Driver not found with ID: " + rideDTO.getDriverId());
        }
        Driver driver = new Driver();
        BeanUtils.copyProperties(driverPresentDTO, driver);

        Ride ride = new Ride();
        BeanUtils.copyProperties(rideDTO, ride);
        ride.setDriver(driver);
        ride.setRideType(RideType.OFFERED);
        Ride savedRide = rideRepository.save(ride);
        log.info("savedRide: {}", savedRide);
        //save ride as OFFERED till now later you can update to COMPLETED if so

        BeanUtils.copyProperties(ride, rideDTO);
        return rideDTO;
    }

    public List<RideDTO> selectRideByRider(RiderRequest riderRequest) {
        log.info("riderRequest {},", riderRequest);

        // Find rides based on source and destination
        List<Ride> matchingRides = rideRepository.findBySourceAndDestinationAndAvailableSeatsGreaterThan(
                riderRequest.getSource(), riderRequest.getDestination(), 0);

        if (matchingRides.isEmpty()) {
            log.info("No matching rides found for the given criteria.");
            return Collections.emptyList();
        }
        List<Ride> availableRides = matchingRides.stream()
                .filter(r -> r.getAvailableSeats() > 0) //this filter can be dynamic
                .collect(Collectors.toList());

        if (availableRides.isEmpty()) {
            log.info("No rides with available seats found.");
            return Collections.emptyList();
        }

        List<Ride> sortedRides;
        switch (riderRequest.getStrategy().toLowerCase()) {
            case "fare":
                sortedRides = availableRides.stream()
                        .filter(ride -> ride.getRideType() == RideType.OFFERED)
                        .sorted(Comparator.comparing(Ride::getFare))
                        .collect(Collectors.toList());
                break;
            case "short":
                sortedRides = availableRides.stream()
                        .filter(ride -> ride.getRideType() == RideType.OFFERED)
                        .sorted(Comparator.comparingInt(Ride::getEstimatedRideTime))
                        .collect(Collectors.toList());
                break;
            default:
                log.error("Invalid strategy: {}", riderRequest.getStrategy());
                throw new RideNotFoundException("Invalid Strategy");
        }
        log.info("sortedRides {}", sortedRides);

        List<RideDTO> rideDTOS = new ArrayList<>();

        for(Ride ride: sortedRides){
            RideDTO rideDTO = new RideDTO();
            BeanUtils.copyProperties(ride,rideDTO);
            //assign driver Id
            rideDTO.setDriverId(ride.getDriver().getUserId());
            rideDTOS.add(rideDTO);
        }
        return rideDTOS;
    }

    @Override
    public RideDTO bookRide(BookRideRequest bookRideRequest) {
        log.info("bookRideRequest {}", bookRideRequest);
        Ride ride = rideRepository.findById(bookRideRequest.getRideId()).orElseThrow(() -> new RideNotFoundException("Ride not found"));

        // Check if rider is already present
        if (ride.getRider() != null) {
            throw new RideAlreadyBookedException("Ride already booked for rider with ID: " + bookRideRequest.getRiderId());
        }

        RiderDTO riderDTO = riderService.getRiderById(bookRideRequest.getRiderId());
        Rider rider = new Rider();
        BeanUtils.copyProperties(riderDTO, rider);

        // Check version to handle concurrency
        if (bookRideRequest.getVersion() != null && !bookRideRequest.getVersion().equals(rideRepository.findById(bookRideRequest.getRideId()).map(Ride::getVersion).orElse(null))) {
            throw new ConcurrencyFailureException("Concurrent update detected for ride with ID: " + bookRideRequest.getRideId());
        }

        // Update the ride
        ride.setRider(rider);
        LocalDateTime currentTime = LocalDateTime.now();
        ride.setStartTime(currentTime);
        ride.setEndTime(currentTime.plusMinutes(ride.getEstimatedRideTime()));
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        ride.setRideType(RideType.COMPLETED);
        rideRepository.save(ride);

        RideDTO rideDTO = new RideDTO();
        BeanUtils.copyProperties(ride, rideDTO);
        rideDTO.setDriverId(ride.getDriver().getUserId());
        rideDTO.setRiderId(bookRideRequest.getRiderId());
        return rideDTO;
    }


    @Override
    public RideDTO bookRideUpdated(String riderId, String rideId) {
        log.info("riderId {}, rideId{}", riderId, rideId);
        Ride ride = rideRepository.findById(rideId).orElse(new Ride());
        // Check if rider is already present
        if (ride.getRider() != null) {
            throw new RideAlreadyBookedException("Ride already booked for rider with ID: " + riderId);
        }

        RiderDTO riderDTO = riderService.getRiderById(riderId);
        Rider rider = new Rider();
        BeanUtils.copyProperties(riderDTO, rider);
        ride.setRider(rider);

        LocalDateTime currentTime = LocalDateTime.now();
        ride.setStartTime(currentTime);
        ride.setEndTime(currentTime.plusMinutes(ride.getEstimatedRideTime()));
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        ride.setRideType(RideType.COMPLETED);
        rideRepository.save(ride);
        RideDTO rideDTO = new RideDTO();
        BeanUtils.copyProperties(ride, rideDTO);
        rideDTO.setDriverId(ride.getDriver().getUserId());
        rideDTO.setRiderId(riderId);
        return rideDTO;
    }

    @Override
    public List<RideDTO> allRidesByDriver(String driverId) {
        log.info("driverId: {}", driverId);
        List<Ride> ridesByDriverId = rideRepository.findByDriver_userId(driverId);
        log.info("ridesByDriverId: {}", ridesByDriverId);

        List<RideDTO> rideDTOList = new ArrayList<>();
        for (Ride ride: ridesByDriverId){
            RideDTO rideDTO = new RideDTO();
            BeanUtils.copyProperties(ride, rideDTO);
            rideDTO.setDriverId(ride.getDriver().getUserId());
            rideDTO.setRiderId(ride.getRider().getUserId());
            rideDTOList.add(rideDTO);
        }
        return rideDTOList;
    }

    @Override
    public List<RideDTO> allRidesByRider(String riderId) {
        log.info("riderId: {}", riderId);
        List<Ride> ridesByRiderId = rideRepository.findByRider_userId(riderId);
        log.info("ridesByRiderId: {}", ridesByRiderId);

        List<RideDTO> rideDTOList = new ArrayList<>();
        for (Ride ride: ridesByRiderId){
            RideDTO rideDTO = new RideDTO();
            BeanUtils.copyProperties(ride, rideDTO);
            rideDTO.setDriverId(ride.getDriver().getUserId());
            rideDTO.setRiderId(ride.getRider().getUserId());
            rideDTOList.add(rideDTO);
        }
        return rideDTOList;
    }

    @Override
    public int getFuelSavedFromAllRides(String driverId) {
        List<RideDTO> rideDTOList = allRidesByDriver(driverId);
        return rideDTOList.stream()
                .mapToInt(RideDTO::getEstimatedRideTime)
                .reduce(0, Integer::sum);
    }

    @Override
    public RideDTO testConcurrency(TestRequest testRequest) {
        log.info("testRequest {}", testRequest);
        Ride ride = rideRepository.findById(testRequest.getRideId()).orElseThrow(() -> new RideNotFoundException("Ride not found"));



        RiderDTO riderDTO = riderService.getRiderById(testRequest.getFirstRiderId());
        Rider rider = new Rider();
        BeanUtils.copyProperties(riderDTO, rider);

        // Update the ride with firstRider
        ride.setRider(rider);
        LocalDateTime currentTime = LocalDateTime.now();
        ride.setStartTime(currentTime);
        ride.setEndTime(currentTime.plusMinutes(ride.getEstimatedRideTime()));
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
        ride.setRideType(RideType.COMPLETED);
        rideRepository.save(ride);

        // Check version to handle concurrency
        if (testRequest.getVersion() != null && !testRequest.getVersion().equals(rideRepository.findById(testRequest.getFirstRiderId()).map(Ride::getVersion).orElse(null))) {
            log.info("Concurrent update detected for ride with ID: {}", testRequest.getSecondRiderId());
            throw new ConcurrencyFailureException("Concurrent update detected for ride with ID: " + testRequest.getSecondRiderId());
        }


        RideDTO rideDTO = new RideDTO();
        BeanUtils.copyProperties(ride, rideDTO);
        rideDTO.setDriverId(ride.getDriver().getUserId());
        rideDTO.setRiderId(testRequest.getFirstRiderId());
        return rideDTO;
    }

//
//    @Override
//    public RideDTO bookRide(String riderId, String rideId) {
//        log.info("riderId {}, rideId{}", riderId, rideId);
//        Ride ride = rideRepository.findById(rideId).orElse(new Ride());
//        ride.setRiderId(riderId);
//        ride.setAvailableSeats(ride.getAvailableSeats() - 1);
//        rideRepository.save(ride);
//        RideHistoryDTO rideHistoryDTO = rideHistoryService.findRideHistoryByDriverId(ride.getDriverId());
//        if (rideHistoryDTO == null || rideHistoryDTO.getRideIds().isEmpty()) {
//            throw new RideHistoryNotFoundException("Ride history not found or is empty");
//        }
//        rideHistoryDTO.setRideType(RideType.COMPLETED);
//        log.info("rideHistoryDTO  {}", rideHistoryDTO);
//        rideHistoryService.createHistory(rideHistoryDTO);
//        return modelMapper.map(ride, RideDTO.class);
//    }
//
//    @Override

//
//    @Override
//    @Transactional
//    public RideDTO bookRideTest(String riderId, String rideId) {
//        try {
//            log.info("riderId {}, rideId{}", riderId, rideId);
//
//            Ride ride = rideRepository.findById(rideId)
//                    .orElseThrow(() -> new RideNotFoundException("Ride not found"));
//
//            if (ride.getAvailableSeats() <= 0) {
//                throw new RuntimeException("No available seats for the ride");
//            }
//
//            ride.setRiderId(riderId);
//            ride.setAvailableSeats(ride.getAvailableSeats() - 1);
//
//            rideRepository.save(ride); //locking
//
//            RideHistoryDTO rideHistoryDTO = rideHistoryService.findRideHistoryByDriverId(ride.getDriverId());
//            if (rideHistoryDTO == null || rideHistoryDTO.getRideIds().isEmpty()) {
//                throw new RideHistoryNotFoundException("Ride history not found or is empty");
//            }
//            rideHistoryDTO.setRideType(RideType.COMPLETED);
//            log.info("rideHistoryDTO  {}", rideHistoryDTO);
//            rideHistoryService.createHistory(rideHistoryDTO);
//
//            return modelMapper.map(ride, RideDTO.class);
//        } catch (OptimisticLockingFailureException ex) {
//            throw new ConcurrencyFailureException("Concurrent update detected. Please try again.");
//        } catch (RideNotFoundException | RideHistoryNotFoundException ex) {
//            throw new RuntimeException("Failed to book ride: " + ex.getMessage());
//        }
//    }
//
//    @Override
//    public int getFuelConsumed(FuelRequest fuelRequest) {
//        Ride ride = rideRepository.findById(fuelRequest.getRideId())
//                .orElseThrow(() -> new RideNotFoundException("Ride not found with ID: " + fuelRequest.getRideId()));
//        return ride.getRideTime();
//    }

}
