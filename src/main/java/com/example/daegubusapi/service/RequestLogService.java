package com.example.daegubusapi.service;

import com.example.daegubusapi.model.RequestLog;
import com.example.daegubusapi.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class RequestLogService {
    @Autowired
    private RequestLogRepository requestLogRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Float> getCommuteTimeForDeepLearning(String userId){
        SimpleDateFormat formatter = new SimpleDateFormat("HH mm");
        List<RequestLog> logs = requestLogRepository.findTop10ByUserIdOrderByRequestTimeDesc(userId);
        List<Float> times = new ArrayList<>();
        for (RequestLog log : logs) {
            StringTokenizer st = new StringTokenizer(formatter.format(log.getRequestTime().getTime())," ");
            int H = Integer.parseInt(st.nextToken());
            float M = Integer.parseInt(st.nextToken())/(float)60;
            float result = H+M;
            times.add(result);
        }
        return times;

    }
//    public List<Float> getCommuteTimeForDeepLearning(String userId) {
//        SimpleDateFormat formatter = new SimpleDateFormat("HH mm");
//        // userId로 검색 쿼리 생성
//        Query query = new Query(Criteria.where("userId").is(userId));
//        // 최근 데이터부터 정렬하도록 설정
//        query.with(Sort.by(Sort.Direction.DESC, "_id"));
//        // 최근 10개의 데이터만 가져오도록 limit 설정
//        query.limit(10);
//        // 프로젝션 설정: requestTime 필드만 가져오도록 설정
//        query.fields().include("requestTime");
//        List<RequestLog> requestLogs = mongoTemplate.find(query, RequestLog.class);
//        List<Float> times = new ArrayList<>();
//        for (RequestLog requestLog : requestLogs) {
//            StringTokenizer st = new StringTokenizer(formatter.format(requestLog.getRequestTime().getTime())," ");
//            int H = Integer.parseInt(st.nextToken());
//            float M = Integer.parseInt(st.nextToken())/(float)60;
//            float result = H+M;
//            times.add(result);
//        }
//        return times;
//    }

    public void recordUserRequest(RequestLog requestLog){
        requestLogRepository.save(requestLog);
    }
    public RequestLog getRecentRequestLog(String userId){
        return requestLogRepository.findTopByUserIdOrderByRequestTimeDesc(userId);
    }
//이건 디비 새로 파자그냥 ...
//    public List<String> getDistinctUserId(){
//        List<RequestLog> distinctTop = requestLogRepository.findDistinctTop();
//        List<String> userIds = new ArrayList<>();
//        for (RequestLog requestLog : distinctTop) {
//            userIds.add(requestLog.getUserId());
//        }
//        return userIds;
//
//    }




}
