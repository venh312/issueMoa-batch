package com.issuemoa.batch.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Component;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class HttpUtil {
    private HttpRequest httpRequest(String url, String data, boolean isPost, String contentType, String authorization) throws URISyntaxException {
        log.info("[HTTP 요청 생성] ==> {}", url);
        // HTTP 요청 생성
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(new URI(url));

        if (isPost)
            requestBuilder = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(data));
        else
            requestBuilder = requestBuilder.GET();

        // Header 추가
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", contentType);

        if (!authorization.isEmpty())
            headers.put("Authorization", authorization);

        for (Map.Entry<String, String> entry : headers.entrySet())
            requestBuilder.header(entry.getKey(), entry.getValue());

        return requestBuilder.build();
    }

    public JSONObject send(String url, String data, boolean isPost, String contentType, String authorization) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = httpRequest(url, data, isPost, contentType, authorization);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200)
                return new JSONObject(response.body());
            else
                log.info("[API 호출 실패. 응답 코드] => {}", statusCode);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public JSONObject sendAsync(String url, String data, boolean isPost, String contentType, String authorization) {
        AtomicReference<JSONObject> jsonObject = null;
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = httpRequest(url, data, isPost, contentType, authorization);
            CompletableFuture<HttpResponse<String>> responseFuture = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 비동기 응답을 처리
            responseFuture.thenAccept(response -> {
                int statusCode = response.statusCode();
                if (statusCode == 200) {
                    try {
                        jsonObject.set(new JSONObject(response.body()));
                    } catch (JSONException e) {
                        log.error(e.getMessage());
                    }
                } else {
                    log.info("[API 호출 실패. 응답 코드] => {}", statusCode);
                }
            }).join(); // 비동기 작업이 완료될 때까지 대기
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return jsonObject.get();
    }

    public NodeList sendAndReceiveXml(String url, String data, boolean isPost, String contentType, String authorization, String tagName) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = httpRequest(url, data, isPost, contentType, authorization);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new ByteArrayInputStream(response.body().getBytes(StandardCharsets.UTF_8)));
                return document.getElementsByTagName(tagName);
            } else {
                log.info("[sendAndReceiveXml Fail] statusCode :: {}", statusCode);
            }
        } catch (Exception e) {
            log.error("Error parsing XML :: " + e.getMessage(), e);
        }
        return null;
    }
}
