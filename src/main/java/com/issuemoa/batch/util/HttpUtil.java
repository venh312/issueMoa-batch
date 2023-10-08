package com.issuemoa.batch.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class HttpUtil {
    private HttpRequest httpRequest(String url, String data, boolean isPost, String contentType, String authorization) throws URISyntaxException {
        // HTTP 요청 생성
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(new URI(url));

        if (isPost)
            requestBuilder = requestBuilder.GET();
        else
            requestBuilder = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(data));

        // Header 추가
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", contentType);

        if (!authorization.isEmpty())
            headers.put("Authorization", authorization);

        for (Map.Entry<String, String> entry : headers.entrySet())
            requestBuilder.header(entry.getKey(), entry.getValue());

        return requestBuilder.build();
    }


    public HashMap<String, Object> send(String url, String data, boolean isPost, String contentType, String authorization) {
        HashMap<String, Object> resultMap = new HashMap<>();

        try {
            // HttpClient 생성
            HttpClient httpClient = HttpClient.newHttpClient();
            // HTTP 요청 생성
            HttpRequest httpRequest = httpRequest(url, data, isPost, contentType, authorization);
            // 동기적으로 API 호출하고 응답을 받음
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 응답 코드 확인 (200은 성공을 의미)
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                // API 응답 데이터 읽기
                String responseBody = response.body();
                // 응답 데이터 출력
                System.out.println(responseBody);
            } else {
                System.out.println("API 호출 실패. 응답 코드: " + statusCode);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return resultMap;
    }

    public HashMap<String, Object> sendAsync(String url, String data, boolean isPost, String contentType, String authorization) {
        HashMap<String, Object> resultMap = new HashMap<>();
        try {
            // HttpClient 생성
            HttpClient httpClient = HttpClient.newHttpClient();
            // HTTP 요청 생성
            HttpRequest httpRequest = httpRequest(url, data, isPost, contentType, authorization);

            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 비동기 응답을 처리
            responseFuture.thenAccept(response -> {
                // 응답 코드 확인 (200은 성공을 의미)
                int statusCode = response.statusCode();
                if (statusCode == 200) {
                    // API 응답 데이터 읽기
                    String responseBody = response.body();
                    // 응답 데이터 출력
                    System.out.println(responseBody);
                } else {
                    System.out.println("API 호출 실패. 응답 코드: " + statusCode);
                }
            }).join(); // 비동기 작업이 완료될 때까지 대기
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
