package com.assignment.cabservice.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderRequest {
    private String riderId;
    private String source;
    private String destination;
    private String strategy;
}
