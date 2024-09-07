package com.issuemoa.batch.infrastructure.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MakeKeywordWriter implements ItemWriter<Map<String, Integer>> {

    @Override
    public void write(List<? extends Map<String, Integer>> items) throws Exception {
        items.forEach((keyword) -> {
            keyword.forEach((s, integer) -> {
                log.info("==> [MakeKeywordWriter] {}, {}", s, integer);
            });
        });
    }
}
