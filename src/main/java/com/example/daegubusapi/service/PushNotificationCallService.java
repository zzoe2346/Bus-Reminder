package com.example.daegubusapi.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PushNotificationCallService {
    public void push() {

        String url = "https://api.flarelane.com/v1/projects/e6060748-0244-4a5b-8029-2c9d6a2f0876/notifications";
        String token = "8R6iUE3lSyz4aW_O0cjFm";

        String requestBody = "{\"targetType\":\"device\",\"targetIds\":[\"88fb7f8b-b66c-4aa6-ba34-a544e4171a4c\"],\"title\":\"안녕하세요\",\"body\":\"빨리 출발하세요\"}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
