package com.issuemoa.batch.infrastructure.writer;

import com.issuemoa.batch.application.KeywordService;
import com.issuemoa.batch.domain.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class MakeKeywordWriter implements ItemWriter<Map<String, Integer>> {
    private final KeywordService keywordService;

    @Override
    public void write(List<? extends Map<String, Integer>> items) throws Exception {
        log.info("==> [MakeKeywordWriter]");
        List<Keyword> keywords = new ArrayList<>();

        items.forEach((data) -> {
            data.forEach((key, count) -> {
                //log.info("==> [MakeKeywordWriter] {}, {}", key, count);
                Keyword keyword = Keyword.builder()
                        .keyword(key)
                        .count(count)
                        .build();
                keywords.add(keyword);
            });
        });

        keywordService.saveAll(keywords);
    }
}
