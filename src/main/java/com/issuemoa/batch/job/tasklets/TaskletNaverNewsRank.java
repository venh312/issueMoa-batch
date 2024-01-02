package com.issuemoa.batch.job.tasklets;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.service.BoardService;
import com.issuemoa.batch.util.CrawlerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
@StepScope
public class TaskletNaverNewsRank implements Tasklet, StepExecutionListener {
    @Value("#{jobParameters[requestDate]}")
    private String requestDate;
    @Value("${endpoint.naver.news.rank}")
    private String endpointNaverNewsRank;

    private final CrawlerUtil crawlerUtil;
    private final BoardService boardService;
    private String exitCode = "FAILED";
    private String exitMessage = "";
    private int size = 0;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("[beforeStep] => " + stepExecution);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext context) {
        final long batchStartTime = System.currentTimeMillis();
        try {
            log.info("[Tasklet 실행 requestDate] => " + requestDate);
            List<Board> list = new ArrayList<>();

            Document contents = crawlerUtil.getContents(endpointNaverNewsRank);
            Elements rankList = contents.select(".rankingnews_list");

            if (!rankList.isEmpty())
                boardService.deleteByType("news");

            for (Element e : rankList) {
                for (Element listContent : e.getElementsByTag("li")) {
                    String title = listContent.select(".list_content a").text();
                    String url = listContent.getElementsByTag("a").attr("href");
                    String src = listContent.select("a > img").attr("src");

                    if (src.isEmpty()) {
                        src = listContent.select("a > img").attr("data-src");
                        if (src.isEmpty())
                            src = listContent.select("a > img").attr("onerror");
                    }

                    Board board = Board.builder()
                        .type("news")
                        .title(title)
                        .url(url)
                        .thumbnail(src)
                        .build();

                    list.add(board);
                }
            }

            size = list.size();
            boardService.saveAll(list);
            this.exitCode = "COMPLETED";

        } catch (Exception e) {
            this.exitMessage = e.getMessage();
        }

        log.info("[" + this.getClass().getSimpleName() + "] :: " + ((System.currentTimeMillis() - batchStartTime) / 1000.0) + " 처리 시간(초)");
        log.info("[" + this.getClass().getSimpleName() + "] :: " + size + "건 등록");

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("[Tasklet 종료] Status => " + stepExecution.getStatus());
//        log.info("[Tasklet 종료] => " + stepExecution);
        return new ExitStatus(exitCode, exitMessage);
    }
}
