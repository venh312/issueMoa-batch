package com.issuemoa.batch.infrastructure.job.configs;

import com.issuemoa.batch.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class JobMakeKeywordConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobMakeKeyword(Step stepMakeKeyword) {
        return jobBuilderFactory.get("jobMakeKeyword")
                .incrementer(new RunIdIncrementer())
                .start(stepMakeKeyword)
                .build();
    }

    @Bean
    public Step stepMakeKeyword(ItemReader<List<Board>> makeKeywordReader,
                                ItemProcessor<List<Board>, Map<String, Integer>> makeKeywordProcessor,
                          ItemWriter<Map<String, Integer>> makeKeywordWriter) {
        return stepBuilderFactory.get("stepMakeKeyword")
                .<List<Board>, Map<String, Integer>>chunk(1) // 한 번에 하나의 List<Board>를 처리
                .reader(makeKeywordReader)
                .processor(makeKeywordProcessor)
                .writer(makeKeywordWriter)
                .build();
    }
}
