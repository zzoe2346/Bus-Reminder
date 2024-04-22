package com.example.daegubusapi.controller;


import com.example.daegubusapi.model.Bus;
import com.example.daegubusapi.model.PredictionRequest;
import com.example.daegubusapi.model.RequestLog;
import com.example.daegubusapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class BusReminderController {
    private final PushNotificationService pushNotificationService;
    private final WebScraperService webScraperService;
    private final ExternalApiService externalApiService;
    private final TargetCheckService targetCheckService;
    private final UserSessionService userSessionService;
    private final RequestLogService requestLogService;
    @Autowired
    UserService userService;

    public BusReminderController(PushNotificationService pushNotificationService, WebScraperService webScraperService, ExternalApiService externalApiService, TargetCheckService targetCheckService, UserSessionService userSessionService, RequestLogService requestLogServicel) {
        this.pushNotificationService = pushNotificationService;
        this.webScraperService = webScraperService;
        this.externalApiService = externalApiService;
        this.targetCheckService = targetCheckService;
        this.userSessionService = userSessionService;
        this.requestLogService = requestLogServicel;
    }




    /**
     * 특정 버스 정류장의 버스 목록을 가져옵니다.
     *
     * @param busStopName The name of the bus stop to retrieve buses for.
     * @return A list of buses available at the specified bus stop.
     */
    @GetMapping("/busstop/{busStopName}/buses")
    public List<Bus> getBusesAtBusStop(@PathVariable String busStopName) {
        List<Bus> buses = webScraperService.retrieveBusesByScraping(busStopName);
        return buses;
    }

    //테스트용 삭제해도 무방
    @GetMapping("/test")
    public List<Bus> testing(@RequestParam String userId) {
        System.out.println("Test Start!");
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("527", 2));
        pushNotificationService.push(userId);
        return buses;
    }
    @GetMapping("/t1")
    public void testi2ng() {
        //Time time = new Time(100);
        LocalTime time = LocalTime.now();
        PredictionRequest predictionRequest = new PredictionRequest("kkoLbllh", time, "DGB7031010800", 5, "156");
        pushNotificationService.askBusReminderStartConfirmation(predictionRequest);
    }

    /**
     * 유저가 신청한 요청을 중지 시킨다
     * @param userId BusReminder실행을 요청한 유저의 식별자
     */
    @GetMapping("/cancel")
    public void cancel(@RequestParam String userId) {
        System.out.println(userId + "의 취소 요청이 왔습니다.");
        userSessionService.setCancel(userId);
    }

    /**
     * BusReminder 를 실행시킨다
     * @param nodeId 정류장의 식별자
     * @param targetNumber 알림을 할 도착까지 남은 정류장 개수
     * @param targetBus 사용자가 원하는 버스
     * @param userId 사용자를 특정하기 위한 식별자
     */
    @GetMapping("/bus-arrival-info")//bus-reminder-run
    public void startBusReminder(@RequestParam String nodeId,
                                 @RequestParam int targetNumber,
                                 @RequestParam String targetBus,
                                 @RequestParam String userId) {

        System.out.println(userId + "로부터 버스알리미 서비스를 요청 받았습니다");
        //로그 기록
        requestLogService.recordUserRequest(new RequestLog(userId,new Date(),nodeId,targetNumber,targetBus));
        //검증
        userSessionService.setUserId(userId);
        //유저 등록
        //userService.enrollUserId(userId);
        //반복적으로 호출
        boolean isValidBus = true;
        while (true) {
            if (userSessionService.isCancel(userId)) {//이거 busremindersrvice로
                System.out.println(userId + "의 취소요청으로인해 중단됩니다");
                break;
            }
            List<Bus> buses = externalApiService.requestBusArrivalInfo(nodeId);
            if (buses.isEmpty()) {
                System.out.println("FAIL FAIL");
            }

            if (targetCheckService.isSuccess(buses, targetBus, targetNumber)) {
                pushNotificationService.push(userId);
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
