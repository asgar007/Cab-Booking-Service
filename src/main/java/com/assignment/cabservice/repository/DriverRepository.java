package com.assignment.cabservice.repository;

import com.assignment.cabservice.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DriverRepository extends MongoRepository<Driver, String> {
    Driver findByUsername(String username);
}
