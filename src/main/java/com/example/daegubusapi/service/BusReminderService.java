package com.example.daegubusapi.service;

import com.example.daegubusapi.model.Bus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BusReminderService {

    public void start(String nodeId,
                       int targetNumber,
                       String targetBus,
                       String userId){

    }

    public void stop(){

    }
    public boolean isBusBeforeTargetStop(List<Bus> buses, String targetBus, int targetNumber){
        System.out.println(targetBus+" 버스가 "+targetNumber+"개 정류장 전인지 확인합니다");
        for (Bus bus : buses) {
            if(bus.getBusNumber().equals(targetBus)){
                if (bus.getRemainingBusStop() == targetNumber) {
                    // System.out.println(bus.getRemainingBusStop());
                    //System.out.println(targetNumber);
                    System.out.println("확인결과: 성공!" +targetBus+" 버스가 "+targetNumber+"개 정류장 전 입니다");
                    return true;
                }
            }
        }
        System.out.println("실패입니다. 곧 다시 버스 도착 정보를 요청합니다...");
        return false;
    }

}
