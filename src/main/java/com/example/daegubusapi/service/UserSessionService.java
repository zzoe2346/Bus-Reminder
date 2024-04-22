package com.example.daegubusapi.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSessionService {
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
