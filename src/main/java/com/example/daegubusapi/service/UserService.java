package com.example.daegubusapi.service;

import com.example.daegubusapi.model.User;
import com.example.daegubusapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<String> getAllUserId(){
        List<String> userIdList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userIdList.add(user.getUserId());
        }
        return userIdList;
    }
    public void enrollUserId(String userId){
        userRepository.save(new User(userId));
    }
}
