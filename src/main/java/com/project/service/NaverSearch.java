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

    public NaverSearch(UseAPI useAPI) {
        this.useAPI = useAPI;
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
        String temp1 = temp.substring(1, temp.length() - 1);

        StringBuffer sf = new StringBuffer(temp1);
        for (int i = 0; i < sf.length(); i++) {
            if (sf.charAt(i) == '=') {
                sf.setCharAt(i, ':');
            }
            if (sf.charAt(i) == '{') {
                sf.insert(i + 1, '"');
                i++;
            } else if (sf.charAt(i) == ':'
                    && (sf.charAt(i + 1) != '/') && sf.charAt(i + 2) != '/') {
                sf.insert(i, '"');
                sf.insert(i + 2, '"');
                i += 2;
            } else if (sf.charAt(i) == ',' && sf.charAt(i + 1) == ' ') {
                sf.insert(i, '"');
                sf.insert(i + 3, '"');
                i += 3;
            } else if (sf.charAt(i) == '}') {
                sf.insert(i, '"');
                i++;
            }
        }

        HashMap<String, String> real = mapper.readValue(sf.toString(), new TypeReference<HashMap<String, String>>() {});

        return real.get("roadAddress");
    }
}
