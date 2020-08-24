package com.project.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class NaverRvrsGeocoding {
    private final UseAPI useAPI;
    private final StringToJson stj;

    public NaverRvrsGeocoding(UseAPI useAPI,
                              StringToJson stj) {
        this.useAPI = useAPI;
        this.stj = stj;
    }

    public String rvrsGeocode(double ddX, double ddY) {
        String header1 = "X-NCP-APIGW-API-KEY-ID";
        String clientId = "ai9wkb82y8";

        String header2 = "X-NCP-APIGW-API-KEY";
        String clientSecret = "OVTmuLyNlXfKimFrcGdu5h5eGqCWp90U8PIaGsDL";

        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?output=json&&orders=addr&coords=";
        String query = String.valueOf(ddX) + "," + String.valueOf(ddY);
        return useAPI.api(apiURL, query, header1, clientId, header2, clientSecret);
    }

    public static void main(String[] args) throws IOException {
        UseAPI useAPI = new UseAPI();
        StringToJson stj = new StringToJson();
        NaverRvrsGeocoding nrg = new NaverRvrsGeocoding(useAPI, stj);
        String test = nrg.rvrsGeocode(126.9763276, 37.5712588);
        System.out.println(test);

        String a = nrg.exportAddress(test);
        System.out.println(a);
    }

    public String exportAddress(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});

        String temp = (map.get("results")).toString();
        temp = temp.substring(1, temp.length() - 1);
        HashMap<String, Object> result1 = mapper.readValue(stj.stringToJson(temp).toString(),
                new TypeReference<HashMap<String, Object>>() {});

        temp = (result1.get("region")).toString();
        result1 = mapper.readValue(stj.stringToJson(temp).toString(),
                new TypeReference<HashMap<String, Object>>() {});

        temp = (result1.get("area3")).toString();
        result1 = mapper.readValue(stj.stringToJson(temp).toString(),
                new TypeReference<HashMap<String, Object>>() {});

        temp = (result1.get("name")).toString();

        return temp.toString();
    }
}
