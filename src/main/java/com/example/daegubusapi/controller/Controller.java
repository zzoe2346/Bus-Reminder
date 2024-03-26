package com.example.daegubusapi.controller;


import com.example.daegubusapi.domain.Bus;
import com.example.daegubusapi.domain.UserIdMap;
import com.example.daegubusapi.service.ApiCallService;
import com.example.daegubusapi.service.PushNotificationCallService;
import com.example.daegubusapi.service.TargetCheckService;
import org.springframework.web.bind.annotation.*;
import com.example.daegubusapi.service.WebScraperService;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private final PushNotificationCallService pushNotificationCallService;
    private final WebScraperService webScraperService;
    private final ApiCallService apiCallService;
    private final TargetCheckService targetCheckService;
    private final UserIdMap userIdMap;

    public Controller(UserIdMap userIdMap,PushNotificationCallService pushNotificationCallService, WebScraperService webScraperService, ApiCallService apiCallService, TargetCheckService targetCheckService) {
        this.pushNotificationCallService = pushNotificationCallService;
        this.webScraperService = webScraperService;
        this.apiCallService = apiCallService;
        this.targetCheckService = targetCheckService;
        this.userIdMap = userIdMap;
    }

    @GetMapping("/busstop/{busStopName}/buses")
    public List<Bus> getBusesAtBusStop(@PathVariable String busStopName) {
        List<Bus> buses = webScraperService.retrieveBusesByScraping(busStopName);
        return buses;


    }

    @GetMapping("/bus")
    public List<Bus> testing() {
        System.out.println("Test Start!");
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("527", 2));
        pushNotificationCallService.push();
        return buses;
    }
    @GetMapping("/cancel")
    public void cancel(@RequestParam String userId){
        userIdMap.setCancel(userId);
    }

    @GetMapping("/bus-arrival-info")
    public void BusScheduleChecker(@RequestParam String nodeId,
                                   @RequestParam int targetNumber,
                                   @RequestParam String targetBus,
                                   @RequestParam String userId,
                                   @RequestParam String deviceId) {

        System.out.println("버스 미리 알림 서비스를 요청 받았습니다");
        System.out.println("deviceId = "+deviceId);
        //검증
        userIdMap.setUserId(userId);
        //반복적으로 호출
        boolean isValidBus = true;
        while (true) {
            if(userIdMap.isCancel(userId)) {
                System.out.println("취소 요청으로인해 루프가 중단됩니다.");
                break;
            }
            List<Bus> buses = apiCallService.call(nodeId);
            if(buses.isEmpty()){
                System.out.println("FAIL FAIL");
            }
            System.out.println("공공 API 호출에 성공했습니다!");
            //제출된 버스 번호가 존재하는지
            //isValidBus=isvalidationServeice.checkBusName(buses);
            //if(!isValidBus) pushNotificationCallService.wrongBusInputMessagePush();

            if (targetCheckService.isSuccess(buses, targetBus, targetNumber)) {
                pushNotificationCallService.push();
                return;
            }
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
