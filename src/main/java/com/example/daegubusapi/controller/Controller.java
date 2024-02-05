package com.example.daegubusapi.controller;



import com.example.daegubusapi.domain.Bus;
import com.example.daegubusapi.service.ApiCallService;
import com.example.daegubusapi.service.PushNotificationCallService;
import com.example.daegubusapi.service.TargetCheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.daegubusapi.service.WebScraperService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    private final PushNotificationCallService pushNotificationCallService;
    private final WebScraperService webScraperService;
    private final ApiCallService apiCallService;
    private final TargetCheckService targetCheckService;

    public Controller(PushNotificationCallService pushNotificationCallService, WebScraperService webScraperService, ApiCallService apiCallService, TargetCheckService targetCheckService) {
        this.pushNotificationCallService = pushNotificationCallService;
        this.webScraperService = webScraperService;
        this.apiCallService = apiCallService;
        this.targetCheckService = targetCheckService;
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
        return buses;
    }

    @GetMapping("/systemStart")
    public void BusScheduleChecker(@RequestParam String nodeId,
                                   @RequestParam int targetNumber,
                                   @RequestParam String targetBus)  {
        System.out.println("run");
        //반복적으로 호출
        while (true) {
            List<Bus> buses = apiCallService.call(nodeId);
            if (targetCheckService.isSuccess(buses,targetBus,targetNumber)) {
                pushNotificationCallService.push();
                return;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }





        //

    }

}
