package com.issuemoa.batch.infrastructure.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class YoutubeUtil {
    @Value("${endpoint.google.youtube.popular}")
    private String endpointYoutubePopular;
    @Value("${api.key.google}")
    private String googleKey;

    private final HttpUtil httpUtil;

    public JSONObject popular(String nextPageToken) {
        String url = endpointYoutubePopular
                + "?part=snippet"
                + "&chart=mostPopular"
                + "&maxResults=50"
                + "&regionCode=kr"
                + "&pageToken=" + nextPageToken
                + "&key=" + googleKey;

        return httpUtil.send(url, "", false, "application/json", "");
    }

}
