package com.example.daegubusapi.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user_requests")
public class UserRequest {
    @Id
    private String id;
    private String userId;
    private Date requestTime;

    public UserRequest(String userId, Date requestTime) {
        this.userId = userId;
        this.requestTime = requestTime;
    }

    public Date getRequestTime() {
        return requestTime;
    }
}
