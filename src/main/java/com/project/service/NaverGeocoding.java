package com.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:properties/naverAPI.properties")
public class NaverGeocoding {

    @Value("${api.X-NCP-APIGW-API-KEY-ID}")
    private String clientId;

    @Value("${api.X-NCP-APIGW-API-KEY}")
    private String clientSecret;

    private final UseAPI useAPI;

    public NaverGeocoding(UseAPI useAPI) {
        this.useAPI = useAPI;
    }

    public String geocode(String address) {
        String header1 = "X-NCP-APIGW-API-KEY-ID";

        String header2 = "X-NCP-APIGW-API-KEY";

        String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";

        return useAPI.api(apiURL, address, header1, clientId, header2, clientSecret);
    }

    public static void main(String[] args) {
        UseAPI useAPI = new UseAPI();
        NaverGeocoding ng = new NaverGeocoding(useAPI);
        System.out.println(ng.geocode("경기도 수원시 팔달구 세지로 406"));
    }
}
