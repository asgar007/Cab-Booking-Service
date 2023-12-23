package com.assignment.cabservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiderDTO {
    private String id;
    @NotBlank
    private String riderName;
    @NotBlank
    private String phoneNumber;
    private String currentLocation;
    private List<String> rideHistoryId; //rideId will be history
}
