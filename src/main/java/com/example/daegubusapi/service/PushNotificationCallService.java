package com.example.daegubusapi.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PushNotificationCallService {
    public void push(String userId) {
        System.out.println(userId+"에게 Push 알림을 합니다");

        String url = "https://api.flarelane.com/v1/projects/e6060748-0244-4a5b-8029-2c9d6a2f0876/notifications";
        String token = "8R6iUE3lSyz4aW_O0cjFm";

        String requestBody = String.format("{\"targetType\":\"userId\",\"targetIds\":[\"%s\"],\"title\":\"출발하세요\",\"body\":\"버스가 지정하신 정류장에 도착했습니다\"}", userId);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Status Code: " + response.statusCode());
            //System.out.println("Response: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
