package com.assignment.cabservice.dto;

import com.assignment.cabservice.constant.RideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideHistoryDTO {
    private String id;
    private String userId;//user: driver or rider
    private RideType rideType; // tells driver or rider
    private List<String> rideIds; // List of ride IDs
}
