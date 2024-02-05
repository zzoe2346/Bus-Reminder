package com.example.daegubusapi.service;

import com.example.daegubusapi.domain.Bus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TargetCheckService {
    public boolean isSuccess(List<Bus> buses, String targetBus, int targetNumber) {
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
