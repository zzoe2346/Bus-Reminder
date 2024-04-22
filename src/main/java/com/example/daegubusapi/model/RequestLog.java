package com.example.daegubusapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user_requests")
public class RequestLog {
    @Id
    private String id;
    private String userId;
    private Date requestTime;
    private String nodeId;
    private int targetNumber;
    private String targetBus;

    public RequestLog( String userId, Date requestTime, String nodeId, int targetNumber, String targetBus) {
        this.userId = userId;
        this.requestTime = requestTime;
        this.nodeId = nodeId;
        this.targetNumber = targetNumber;
        this.targetBus = targetBus;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public String getTargetBus() {
        return targetBus;
    }

    public Date getRequestTime() {
        return requestTime;
    }
}
