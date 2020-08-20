package com.project.service;

import org.springframework.stereotype.Service;

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
}
