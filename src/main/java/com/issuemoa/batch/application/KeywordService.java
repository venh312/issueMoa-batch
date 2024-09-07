package com.issuemoa.batch.application;

import com.issuemoa.batch.domain.keyword.Keyword;
import com.issuemoa.batch.domain.keyword.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public void saveAll(List<Keyword> keywords) {
        keywordRepository.saveAll(keywords);
    }
}
