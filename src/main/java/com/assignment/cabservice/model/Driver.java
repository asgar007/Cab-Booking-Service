package com.assignment.cabservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "drivers")
public class Driver extends User{
    private String licenseNumber;
    @DBRef
    private Vehicle vehicle;
    private double rating;
    private String status; // Online/Offline

}