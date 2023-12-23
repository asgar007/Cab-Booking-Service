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
    private final ModelMapper modelMapper;
    @Override
    public RiderDTO createRider(RiderDTO riderDTO) {
        Rider rider=new Rider();
        BeanUtils.copyProperties(riderDTO,rider);
        log.info("rider : {}",rider);
        return modelMapper.map(riderRepository.save(rider), RiderDTO.class);
    }
}
