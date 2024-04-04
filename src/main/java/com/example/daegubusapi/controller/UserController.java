package com.example.daegubusapi.controller;

import com.example.daegubusapi.domain.UserRequest;
import com.example.daegubusapi.service.UserRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserRequestService userRequestService;

    //저장
    @GetMapping("/request")
    public String recordRequest(@RequestParam String userId) {
        userRequestService.recordUserRequest(userId);
        return "User connected successfully.";
    }
    @GetMapping("/find")
    public List<Float> findRequests(@RequestParam String userId) {
        return userRequestService.findDateByUserId(userId);
    }


}

