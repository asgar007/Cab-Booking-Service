package com.assignment.cabservice.model;

import com.assignment.cabservice.constant.RideType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "rideHistories")
public class RideHistory {
    @Id
    private String id;
    @Indexed
    private String userId;
    private RideType rideType;
    private List<String> rideIds; // List of ride IDs
}
