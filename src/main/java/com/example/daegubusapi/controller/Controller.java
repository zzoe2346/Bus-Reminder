package com.example.daegubusapi.controller;



import com.example.daegubusapi.domain.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.daegubusapi.service.WebScrapingService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    WebScrapingService webScrapingService;
    @GetMapping("/busstop/{busStopName}/buses")
    public List<Bus> getBusesAtBusStop(@PathVariable String busStopName) {
        List<Bus> buses = webScrapingService.retrieveBusesByScraping(busStopName);
        return buses;
    }

    @GetMapping("/bus")
    public List<Bus> testing() {
        System.out.println("Test Start!");
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("527", 2));
        return buses;
    }
}
