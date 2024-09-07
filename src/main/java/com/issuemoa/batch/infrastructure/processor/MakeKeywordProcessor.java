package com.issuemoa.batch.infrastructure.processor;

import com.issuemoa.batch.domain.board.Board;
import lombok.extern.slf4j.Slf4j;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MakeKeywordProcessor implements ItemProcessor<List<Board>, Map<String, Integer>> {

    @Override
    public Map<String, Integer> process(List<Board> boards) throws Exception {
        log.info("==> [MakeKeywordProcessor]");

        List<String> keywords = new ArrayList<>();
        boards.forEach(board -> {
            CharSequence normalized = OpenKoreanTextProcessorJava.normalize(board.getTitle());

            // Tokenize
            Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);

            // 전부 포함
            //List<KoreanTokenJava> tokenList = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);

            // Phrase Extraction (명사, 해시태그만)
            List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);

            phrases.forEach(phrase -> keywords.add(phrase.text()));
        });

        Map<String, Integer> keywordCounts = new HashMap<>();
        keywords.forEach(keyword -> {
            keywordCounts.put(keyword, keywordCounts.getOrDefault(keyword, 0) + 1);
        });

        return keywordCounts;
    }
}
