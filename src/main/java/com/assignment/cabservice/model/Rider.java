package com.assignment.cabservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "riders")
public class Rider{
    @Id
    private String id;
    @Indexed(unique = true)
    private String riderName;
    private String phoneNumber;
    private String currentLocation;
    private List<String> rideHistoryId; //rideId will be history
}
