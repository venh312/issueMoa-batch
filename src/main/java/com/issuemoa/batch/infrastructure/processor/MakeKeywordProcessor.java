package com.issuemoa.batch.infrastructure.processor;

import com.issuemoa.batch.domain.board.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MakeKeywordProcessor implements ItemProcessor<List<Board>, Map<String, Integer>> {

    @Override
    public Map<String, Integer> process(List<Board> boards) throws Exception {
        log.info("==> [MakeKeywordProcessor] process");
        Map<String, Map<String, Integer>> result = new HashMap<>();

        StringBuilder keywords = new StringBuilder();
        boards.forEach(board -> {
            keywords.append(" ").append(board.getTitle());
        });

        Map<String, Integer> keywordCounts = new HashMap<>();

        String[] titles = keywords.toString().split("\\s+");
        Arrays.stream(titles).forEach(title -> {
            String cleanTitle = title.replaceAll("[^\\uAC00-\\uD7AF]", "");
            if (cleanTitle.length() > 1) {
                keywordCounts.put(cleanTitle, keywordCounts.getOrDefault(cleanTitle, 0) + 1);
            }
        });

        return keywordCounts;
    }
}
