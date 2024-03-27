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

    public Controller(UserIdMap userIdMap, PushNotificationCallService pushNotificationCallService, WebScraperService webScraperService, ApiCallService apiCallService, TargetCheckService targetCheckService) {
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

    @GetMapping("/test")
    public List<Bus> testing(@RequestParam String userId) {
        System.out.println("Test Start!");
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("527", 2));
        pushNotificationCallService.push(userId);
        return buses;
    }

    @GetMapping("/cancel")
    public void cancel(@RequestParam String userId) {
        System.out.println(userId + "의 취소 요청이 왔습니다.");
        userIdMap.setCancel(userId);
    }

    @GetMapping("/bus-arrival-info")
    public void BusScheduleChecker(@RequestParam String nodeId,
                                   @RequestParam int targetNumber,
                                   @RequestParam String targetBus,
                                   @RequestParam String userId) {

        System.out.println(userId + "로부터 버스알리미 서비스를 요청 받았습니다");
        //검증
        userIdMap.setUserId(userId);
        //반복적으로 호출
        boolean isValidBus = true;
        while (true) {
            if (userIdMap.isCancel(userId)) {
                System.out.println(userId + "의 취소요청으로인해 중단됩니다");
                break;
            }
            List<Bus> buses = apiCallService.call(nodeId);
            if (buses.isEmpty()) {
                System.out.println("FAIL FAIL");
            }

            if (targetCheckService.isSuccess(buses, targetBus, targetNumber)) {
                pushNotificationCallService.push(userId);
                System.out.println(userId+"의 요청이 완료되었습니다...");
                return;
            }
            int count = 0;
            while (true) {
                if(count==19) break;
                System.out.print("◼\uFE0E ");
                count++;
                try {
                    Thread.sleep(1000); // 1초 대기
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
//                    e.printStackTrace();
                }
            }
            System.out.println();
//            try {
//                Thread.sleep(20000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

}
