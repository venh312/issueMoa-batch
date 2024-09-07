package com.issuemoa.batch.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class CrawlerUtil {
    public Document getContents(String url) throws IOException {
        // Connection 생성
        Connection conn = Jsoup.connect(url);
        return conn.get();
    }
}
