package com.example.daegubusapi.repository;

import com.example.daegubusapi.model.PredictionRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PredictionRepository extends MongoRepository<PredictionRequest, String> {

}
