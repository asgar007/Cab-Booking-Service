package com.assignment.cabservice.service;

import com.assignment.cabservice.constant.RideType;
import com.assignment.cabservice.dto.RideHistoryDTO;
import com.assignment.cabservice.helper.RideTypeRequest;
import com.assignment.cabservice.model.RideHistory;
import com.assignment.cabservice.repository.RideHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideHistoryServiceImpl implements RideHistoryService{

    private final RideHistoryRepository rideHistoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public RideHistoryDTO createHistory(RideHistoryDTO rideHistoryDTO) {
        RideHistory rideHistory = new RideHistory();
        BeanUtils.copyProperties(rideHistoryDTO,rideHistory);
        return modelMapper.map(rideHistoryRepository.save(rideHistory), RideHistoryDTO.class);
    }

    @Override
    public RideHistoryDTO findRideHistoryByDriverId(String driverId) {
        return modelMapper.map(rideHistoryRepository.findByUserId(driverId).orElseGet(RideHistory::new), RideHistoryDTO.class);
    }

    @Override
    public List<RideHistoryDTO> getRidesByRideType(RideTypeRequest rideTypeRequest) {

        List<RideHistory> rideHistoryList = rideHistoryRepository.findByRideType(rideTypeRequest.getRideType());
        log.info("rideHistoryList {}",rideHistoryList);

        return rideHistoryList.stream()
                .map(rideHistory -> modelMapper.map(rideHistory, RideHistoryDTO.class))
                .collect(Collectors.toList());
    }



}
