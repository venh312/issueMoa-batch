package com.issuemoa.batch.job.configs;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.service.BoardService;
import com.issuemoa.batch.util.CrawlerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobNaverNewsRankConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CrawlerUtil crawlerUtil;
    private final BoardService boardService;

    @Value("${endpoint.naver.news.rank}")
    private String endpointNaverNewsRank;
    @Bean
    public Job jobNaverNewsRank() {
        return jobBuilderFactory.get("jobNaverNewsRank")
            .start(stepNaverNewsRank())
            .build();
    }
    @Bean
    public Step stepNaverNewsRank() {
        return stepBuilderFactory.get("stepNaverNewsRank")
            .tasklet((contribution, chunkContext) -> {
                log.info("stepNaverNewsRank");

                List<Board> list = new ArrayList<>();
                try {
                    Document contents = crawlerUtil.getContents(endpointNaverNewsRank);
                    Elements rankList = contents.select(".rankingnews_list");
                    for (Element elm : rankList) {
                        Elements li = elm.getElementsByTag("li");
                        for (Element listContent : li) {
                            String title = listContent.select(".list_content a").text();
                            String content = listContent.getElementsByTag("a").attr("href");
                            String src = listContent.select("a > img").attr("data-src");

                            src = !src.isEmpty() ? src : listContent.select("a > img").attr("onerror");

                            Board board = Board.builder()
                                .type("NEWS")
                                .allTimeYn("Y")
                                .title(title)
                                .contents(content)
                                .url(src)
                                .build();

                            list.add(board);
                        }
                    }
                } catch (IOException e) {
                    log.error(e.getMessage());
                }

                boardService.saveAll(list);

                return RepeatStatus.FINISHED;
            })
            .build();

//        return stepBuilderFactory.get("stepPullNaverNews")
//            .tasklet((contribution, chunkContext) -> {
//                log.info("stepPullNaverNews");
//                String query = URLEncoder.encode("주식", "UTF-8");
//
//                HashMap<String, Object> result = httpUtil.send(endpointNews + "?query=" + query, "", false, "application/json", "");
//                ArrayList<HashMap<String, String>> items = (ArrayList<HashMap<String, String>>) result.get("items");
//                ArrayList<Board> saveData = new ArrayList<>();
//
//                for (HashMap<String, String> br : items)
//                    saveData.add(new Board("NEWS", "Y", br.get("title"), br.get("description"), ""));
//
//                boardRepository.saveAll(saveData);
//
//                return RepeatStatus.FINISHED;
//            })
//            .build();
    }
}
