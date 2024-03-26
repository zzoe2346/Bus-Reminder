package com.example.daegubusapi.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserIdMap {
    private Map<String, Boolean> map = new HashMap<>();

    public void setUserId(String userId) {
        map.put(userId,false);
    }
    public void setCancel(String userId) {
        map.put(userId, true);
    }
    public void removeUserId(String userId) {
        map.remove(userId);
    }

    public Boolean isCancel(String userId) {
        return map.get(userId);
    }
}
