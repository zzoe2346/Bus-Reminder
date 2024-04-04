package com.example.daegubusapi.repository;

import com.example.daegubusapi.domain.UserRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRequestRepository extends MongoRepository<UserRequest,String> {
}
