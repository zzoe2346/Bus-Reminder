package com.example.daegubusapi.service;

import com.example.daegubusapi.domain.UserRequest;
import com.example.daegubusapi.repository.UserRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class UserRequestService {
    @Autowired
    private UserRequestRepository userRequestRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Float> findDateByUserId(String userId) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH mm");
        // userId로 검색 쿼리 생성
        Query query = new Query(Criteria.where("userId").is(userId));
        // 최근 데이터부터 정렬하도록 설정
        query.with(Sort.by(Sort.Direction.DESC, "_id"));
        // 최근 10개의 데이터만 가져오도록 limit 설정
        query.limit(10);
        List<UserRequest> userRequests = mongoTemplate.find(query, UserRequest.class);
        List<Float> times = new ArrayList<>();
        for (UserRequest userRequest : userRequests) {
            StringTokenizer st = new StringTokenizer(formatter.format(userRequest.getRequestTime().getTime())," ");
            int H = Integer.parseInt(st.nextToken());
            float M = Integer.parseInt(st.nextToken())/(float)60;
            float result = H+M;
            times.add(result);
        }
        return times;
    }

    public void recordUserRequest(String userId){
        UserRequest userRequest = new UserRequest(userId, new Date());
        userRequestRepository.save(userRequest);
        //userRequestRepository.insert(userRequest);
        //mongoTemplate.insert(userRequest);
    }


}
