package com.example.daegubusapi.service;

import com.example.daegubusapi.model.PredictionRequest;
import com.example.daegubusapi.model.RequestLog;
import com.example.daegubusapi.repository.PredictionRepository;
import org.openqa.selenium.logging.LocalLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class PredictionService {
    @Autowired
    PredictionRepository predictionRepository;
    @Autowired
    RequestLogService requestLogService;
    @Autowired
    PushNotificationService pushNotificationService;
    @Autowired
    BusReminderService busReminderService;
    public void updatePrediction(String userId, LocalTime predictionTime){
        RequestLog requestLog = requestLogService.getRecentRequestLog(userId);
        predictionRepository.save(new PredictionRequest(userId, predictionTime, requestLog.getNodeId(), requestLog.getTargetNumber(), requestLog.getTargetBus()));
    }


    @Scheduled(cron = "0 * 5-9 * * ?")
    public void autoStartBusReminder() {
        // 여기에 스케줄링할 작업 내용을 넣어주세요
        List<PredictionRequest> predictionRepositoryAll = predictionRepository.findAll();
        //시작하시겠습니까? 알람을 보내자... 이걸 클릭하면 실행되도록!
        //리스트에서 지금시각에서10분 이후꺼들 얻고 이겄들에 알람 준다.
        for (PredictionRequest predictionRequest : predictionRepositoryAll) {
            LocalTime predictionTime = predictionRequest.getPredictionTIme();
            LocalDateTime presentTime = LocalDateTime.now();
            Duration duration = Duration.between(predictionTime, presentTime);

            if(duration.getSeconds()<600){
                pushNotificationService.askBusReminderStartConfirmation(predictionRequest);
            }

        }
    }
}
