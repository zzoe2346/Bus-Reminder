package com.example.daegubusapi.service;

import com.example.daegubusapi.model.Bus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalApiService {
    public List<Bus> requestBusArrivalInfo(String nodeId) {
        System.out.println(nodeId + "정류소의 버스 도착 정보를 요청했습니다");
        String url = "https://apis.data.go.kr/1613000/ArvlInfoInqireService/getSttnAcctoArvlPrearngeInfoList?serviceKey=KaMN32QU5iddZ2p7xxSw3BodH5qyNiC2WYFDg2bQMK7QT2SdOitDuL8YT23425Eu7QkCnfmZZpd9rM9RXc4bTA==&pageNo=1&numOfRows=10&_type=json&cityCode=22&nodeId="
                + nodeId;

        // Create RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make a GET request to the API
        String json = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // "item" 리스트에 해당하는 노드 가져오기
        JsonNode itemList = jsonNode
                .path("response")
                .path("body")
                .path("items")
                .path("item");

        List<Bus> buses = new ArrayList<>();
        // "item" 리스트가 배열이라면 처리
        if (itemList.isArray()) {
            for (JsonNode itemNode : itemList) {
                // 각각의 아이템에 대한 처리
                String busNumber = itemNode.path("routeno").asText();
                int left = Integer.parseInt(itemNode.path("arrprevstationcnt").asText());
//                System.out.println("남은 정류장: " + itemNode.path("arrprevstationcnt").asText());
//                System.out.println("버스 번호: " + itemNode.path("routeno").asText());
                buses.add(new Bus(busNumber, left));
                // 다른 필드들도 필요한대로 추가
            }
        } else {
            // "item" 리스트가 배열이 아닌 경우 예외처리 또는 로그 출력 등을 수행
            System.out.println("Item list is not an array.");
        }

        for (Bus bus : buses) {
            System.out.println("버스번호: " + bus.getBusNumber() + " 남은 정류장 수: " + bus.getRemainingBusStop());
        }
        return buses;
    }
}
