package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
@PropertySource("classpath:properties/naverAPI.properties")
public class NaverSearch {

    @Value("${api.X-Naver-Client-Id}")
    private String clientId;

    @Value("${api.X-Naver-Client-Secret}")
    private String clientSecret;

    private final UseAPI useAPI;
    private final StringToJson stj;

    public NaverSearch(UseAPI useAPI,
                       StringToJson stj) {
        this.useAPI = useAPI;
        this.stj = stj;
    }

    public String search(String dong, String ocrResult) {
        String header1 = "X-Naver-Client-Id";

        String header2 = "X-Naver-Client-Secret";

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=";
        String query = dong + " " + ocrResult;

        String searchResult = useAPI.api(apiURL, query, header1, clientId, header2, clientSecret);
        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> map = mapper.readValue(searchResult, new TypeReference<HashMap<String, Object>>() {});
            System.out.println(map.get("items"));
            // 검색 결과가 없다면 'items'는 "[]"를 반환한다.
            if (map.get("items").toString().equals("[]")) {
                // starbucks의 경우 'starbucks coffee'로 검색하면 결과가 나오지 않기 때문에 " "로 스플릿하고
                // 'starbucks'로만 검색하게 한다.
                String[] temp = ocrResult.split(" ");
                query = dong + " " + temp[0];
                searchResult = useAPI.api(apiURL, query, header1, clientId, header2, clientSecret);
            }
        } catch (Exception e) {
            System.out.println("jackson err: " + e);
        }

        return searchResult;
    }

    public String exportRoadAddress(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});

            String temp = (map.get("items")).toString();
            temp = temp.substring(1, temp.length() - 1);

            HashMap<String, String> real = mapper.readValue(stj.stringToJson(temp).toString(),
                    new TypeReference<HashMap<String, String>>() {});

            return real.get("roadAddress");
        } catch (Exception e) {
            return "NotFoundErr";
        }
    }
}
