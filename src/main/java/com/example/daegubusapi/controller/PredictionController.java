package com.example.daegubusapi.controller;

import com.example.daegubusapi.service.PredictionService;
import com.example.daegubusapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@RestController
public class PredictionController {

    @Autowired
    PredictionService predictionService;
    @Autowired
    UserService userService;

    @GetMapping("/update")
    public void updatePredictionTime(@RequestParam String userId,
                                     @RequestParam LocalTime time) {
        predictionService.updatePrediction(userId, time);
    }

    @GetMapping("/getAllUserId")
    public List<String> getAllUserId() {
        return userService.getAllUserId();
    }
    @GetMapping("/tt")
    public String tt() {
        for (int i = 1; i < 10; i++) {
            userService.enrollUserId(String.valueOf(i));
        }

        return "hello";

    }


}
