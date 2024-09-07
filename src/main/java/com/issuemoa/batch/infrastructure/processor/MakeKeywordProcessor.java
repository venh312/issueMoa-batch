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

            // 한국어를 처리하는 예시입니다ㅋㅋㅋ #한국어

            // Tokenize
            Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
            // [한국어, 를, 처리, 하는, 예시, 입니, 다, ㅋㅋ, #한국어]

            //List<KoreanTokenJava> tokenList = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);
            //[한국어(Noun: 0, 3), 를(Josa: 3, 1), 처리(Noun: 5, 2), 하는(Verb(하다): 7, 2), 예시(Noun: 10, 2), 입니다(Adjective(이다): 12, 3), ㅋㅋㅋ(KoreanParticle: 15, 3), #한국어(Hashtag: 19, 4)]

            // Phrase Extraction
            List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);
            //[한국어(Noun: 0, 3), 처리(Noun: 5, 2), 처리하는 예시(Noun: 5, 7), 예시(Noun: 10, 2), #한국어(Hashtag: 18, 4)]
            //log.info("==> phrases :: {}", phrases);
            phrases.forEach(phrase -> keywords.add(phrase.text()));
        });

        Map<String, Integer> keywordCounts = new HashMap<>();
        keywords.forEach(keyword -> {
            keywordCounts.put(keyword, keywordCounts.getOrDefault(keyword, 0) + 1);
        });

        return keywordCounts;
    }
}
