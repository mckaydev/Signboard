package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class NaverSearch {
    private final UseAPI useAPI;
    private final StringToJson stj;

    public NaverSearch(UseAPI useAPI,
                       StringToJson stj) {
        this.useAPI = useAPI;
        this.stj = stj;
    }

    public String search(String ocrResult) {
        String header1 = "X-Naver-Client-Id";
        String clientId = "hKzNsSuDTyAaWBoxl5HE";

        String header2 = "X-Naver-Client-Secret";
        String clientSecret = "z2lUNQG3GR";

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=";

        return useAPI.api(apiURL, ocrResult, header1, clientId, header2, clientSecret);
    }

    public String exportRoadAddress(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});

        String temp = (map.get("items")).toString();
        temp = temp.substring(1, temp.length() - 1);

        HashMap<String, String> real = mapper.readValue(stj.stringToJson(temp).toString(),
                new TypeReference<HashMap<String, String>>() {});

        return real.get("roadAddress");
    }
}
