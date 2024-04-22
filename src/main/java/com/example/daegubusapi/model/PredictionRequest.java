package com.example.daegubusapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Document(collection = "prediction_requests")

public class PredictionRequest {
    @Id
    private String userId;
    private LocalTime predictionTIme;
    private String nodeId;
    private int targetNumber;
    private String targetBus;

    public PredictionRequest(String userId, LocalTime predictionTIme, String nodeId, int targetNumber, String targetBus) {
        this.userId = userId;
        this.predictionTIme = predictionTIme;
        this.nodeId = nodeId;
        this.targetNumber = targetNumber;
        this.targetBus = targetBus;
    }

    public String getUserId() {
        return userId;
    }

    public LocalTime getPredictionTIme() {
        return predictionTIme;
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
}
