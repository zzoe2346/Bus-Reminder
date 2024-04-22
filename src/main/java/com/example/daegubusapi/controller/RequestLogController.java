package com.example.daegubusapi.controller;

import com.example.daegubusapi.model.PredictionRequest;
import com.example.daegubusapi.model.RequestLog;
import com.example.daegubusapi.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.List;

@RestController
public class RequestLogController {
    @Autowired
    RequestLogService requestLogService;
    //새로운녀셕으로...
//    @GetMapping("/getUserIdForPredictionUpdate")
//    public List<String> findPredictionServiceApplicantUserIdList() {
//        return requestLogService.getDistinctUserId();
//    }

    @GetMapping("/r")
    public String r() {
        Time currentTime = new Time(System.currentTimeMillis());
        RequestLog requestLog = new RequestLog("1",currentTime,"DGB7031010800", 5, "156");
        requestLogService.recordUserRequest(requestLog);
        return "success";
    }

    @GetMapping("/getRequestTimeLog")
    public List<Float> findRequests(@RequestParam String userId) {
        return requestLogService.getCommuteTimeForDeepLearning(userId);//성공!!!
        //return requestLogService.getCommuteTimeForDeepLearning(userId);
    }


}

