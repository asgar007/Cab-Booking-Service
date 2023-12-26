package com.assignment.cabservice.service;

import com.assignment.cabservice.dto.RiderDTO;

public interface RiderService {
    RiderDTO createRider(RiderDTO riderDTO);
    RiderDTO getRiderById(String id);

}
