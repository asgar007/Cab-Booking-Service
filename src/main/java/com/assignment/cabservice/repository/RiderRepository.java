package com.assignment.cabservice.repository;

import com.assignment.cabservice.model.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RiderRepository extends MongoRepository<Rider, String> {
}
