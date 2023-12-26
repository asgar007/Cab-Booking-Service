package com.assignment.cabservice.helper;

import com.assignment.cabservice.constant.RideType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideTypeRequest {
    private String userId;
    private RideType rideType;
}
