package com.example.daegubusapi.repository;

import com.example.daegubusapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
