package com.issuemoa.batch.job.tasklets;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.service.BoardService;
import com.issuemoa.batch.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
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
public class TaskletYoutubePopular implements Tasklet, StepExecutionListener {
    @Value("#{jobParameters[requestDate]}")
    private String requestDate;
    @Value("${endpoint.google.youtube.popular}")
    private String endpointYoutubePopular;
    @Value("${api.key.google}")
    private String googleKey;

    private final HttpUtil httpUtil;
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
            log.info("[Tasklet 실행 requestDate] => {}", requestDate);
            List<Board> list = new ArrayList<>();

            String url = endpointYoutubePopular
                    + "?part=snippet"
                    + "&chart=mostPopular"
                    + "&maxResults=50"
                    + "&regionCode=kr"
                    + "&key=" + googleKey;

            JSONObject result = httpUtil.send(url, "", false, "application/json", "");
            JSONArray jsonArray = result.getJSONArray("items");

            // 기존 인기 동영상 목록 삭제
            if (jsonArray.length() > 0)
                boardService.deleteByType("youtube");

            for (int i = 0, n = jsonArray.length(); i < n; i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject snippet = obj.getJSONObject("snippet");

                Board board = Board.builder()
                    .type("youtube")
                    .title(snippet.getString("title"))
                    .contents(snippet.getString("description"))
                    .url(obj.getString("id"))
                    .build();

                list.add(board);
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
