package com.example.daegubusapi.controller;



import com.example.daegubusapi.Bus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.daegubusapi.service.ApiService;

import java.util.List;

@RestController
public class ApiController {
    @GetMapping("/busstop/{busStopName}/buses")
    public List<Bus> getBusesAtBusStop(@PathVariable String busStopName) {
        List<Bus> buses = ApiService.retrieveBusesByScraping(busStopName);
        return buses;
    }
}
