package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.RiderDTO;
import com.assignment.cabservice.model.Rider;
import com.assignment.cabservice.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiderServiceImpl implements RiderService{

    private final RiderRepository riderRepository;

    @Override
    public RiderDTO createRider(RiderDTO riderDTO) {
        log.info("riderDTO : {}",riderDTO);
        Rider rider=new Rider();
        BeanUtils.copyProperties(riderDTO,rider);
        rider = riderRepository.save(rider);
        log.info("rider : {}",rider);
        BeanUtils.copyProperties(rider, riderDTO);
        return riderDTO;
    }

    @Override
    public RiderDTO getRiderById(String id) {
        Rider rider = riderRepository.findById(id).orElseGet(Rider::new);
        RiderDTO riderDTO = new RiderDTO();
        BeanUtils.copyProperties(rider, riderDTO);
        return riderDTO;
    }
}
