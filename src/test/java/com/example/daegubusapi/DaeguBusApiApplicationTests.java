package com.example.daegubusapi;

import com.example.daegubusapi.service.ApiCallService;
import com.example.daegubusapi.service.PushNotificationCallService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DaeguBusApiApplicationTests {

    @Autowired
    ApiCallService apiCallService;
    @Autowired
    PushNotificationCallService pushNotificationCallService;

    @Test
    void contextLoads() throws JsonProcessingException {
        apiCallService.call("DGB7031010800");
        pushNotificationCallService.push();
    }

}
