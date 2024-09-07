package com.issuemoa.batch.infrastructure.job.tasklets;

import com.issuemoa.batch.domain.board.Board;
import com.issuemoa.batch.application.BoardService;
import com.issuemoa.batch.infrastructure.utils.YoutubeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
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
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
@StepScope
public class TaskletYoutubePopular implements Tasklet, StepExecutionListener {
    @Value("#{jobParameters[requestDate]}")
    private String requestDate;

    private final YoutubeUtil youtubeUtil;
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

            List<JSONArray> jsonArrayList = new ArrayList<>();
            String nextPageToken = "";

            do {
                JSONObject result = youtubeUtil.popular(nextPageToken);
                addItemsToArrayList(result, jsonArrayList);
                nextPageToken = updateNextPageToken(result);
            } while (!nextPageToken.isEmpty());

            List<Board> list = createBoardList(jsonArrayList);
            Set<String> urls = list.stream().map(Board::getUrl).collect(Collectors.toSet());

            List<Board> existing = boardService.findByUrlIn(urls);
            Set<String> existingUrl = existing.stream().map(Board::getUrl).collect(Collectors.toSet());

            List<Board> newDataList = list.stream().filter(data -> !existingUrl.contains(data.getUrl())).collect(Collectors.toList());

            size = newDataList.size();
            boardService.saveAll(newDataList);

            this.exitCode = "COMPLETED";
        } catch (Exception e) {
            log.error("Exception occurred: {}", e.getMessage(), e);
            this.exitMessage = e.getMessage();
        }

        log.info("[" + this.getClass().getSimpleName() + "] :: " + ((System.currentTimeMillis() - batchStartTime) / 1000.0) + " 처리 시간(초)");
        log.info("[" + this.getClass().getSimpleName() + "] :: " + size + "건 등록");

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("[TaskletYoutubePopular 종료] Status => " + stepExecution.getStatus());
        return new ExitStatus(exitCode, exitMessage);
    }

    private void addItemsToArrayList(JSONObject result, List<JSONArray> jsonArrayList) throws JSONException {
        if (result.has("items"))
            jsonArrayList.add(result.getJSONArray("items"));
    }

    private String updateNextPageToken(JSONObject result) throws JSONException {
        return result.has("nextPageToken") ? (String) result.get("nextPageToken") : "";
    }

    private List<Board> createBoardList(List<JSONArray> jsonArrayList) throws JSONException {
        List<Board> list = new ArrayList<>();

        for (JSONArray jsonArray : jsonArrayList) {
            for (int j = 0, m = jsonArray.length(); j < m; j++) {
                JSONObject obj = jsonArray.getJSONObject(j);
                JSONObject snippet = obj.getJSONObject("snippet");
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                JSONObject thumbnailsObj = thumbnails.getJSONObject("default");

                Board board = Board.builder()
                        .type("youtube")
                        .title(snippet.getString("title"))
                        .contents(snippet.getString("description"))
                        .url(obj.getString("id"))
                        .thumbnail(thumbnailsObj.getString("url"))
                        .build();

                list.add(board);
            }
        }

        return list;
    }
}
