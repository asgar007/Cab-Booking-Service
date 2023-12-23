package com.assignment.cabservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FuelSavingDTO {
    private String id;
    private String driverId;
    private int hoursSaved; // Hours of ride equals liters of fuel saved
}