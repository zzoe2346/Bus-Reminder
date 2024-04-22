package com.example.daegubusapi.repository;

import com.example.daegubusapi.model.RequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RequestLogRepository extends MongoRepository<RequestLog,String> {
    //YourDocument findByIdOrderByCreatedAtDesc(String id);
    //findTopByUserIdOrderByRequestTimeDesc
    //RequestLog findTopByUserIdOrderByrequestTimeDesc(String userId);
    //    List<Request> findTop10ByUserIdOrderByRequestTimeDesc(String userId, Pageable pageable);
    RequestLog findTopByUserIdOrderByRequestTimeDesc(String userId);
    List<RequestLog> findTop10ByUserIdOrderByRequestTimeDesc(String userId);
}
