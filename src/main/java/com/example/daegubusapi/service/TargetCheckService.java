package com.example.daegubusapi.service;

import com.example.daegubusapi.domain.Bus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TargetCheckService {
    public boolean isSuccess(List<Bus> buses, String targetBus, int targetNumber) {

        System.out.println("버스가 사용자의 요청 수치에 올때까지 반복해서 호출합니다...");
        for (Bus bus : buses) {
            if(bus.getBusNumber().equals(targetBus)){
                if (bus.getRemainingBusStop() == targetNumber) {
                    System.out.println(bus.getRemainingBusStop());
                    System.out.println(targetNumber);
                    return true;
                }
            }
        }
        return false;

    }
}
