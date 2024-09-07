package com.issuemoa.batch.infrastructure.job.configs;

import com.issuemoa.batch.infrastructure.job.tasklets.TaskletNaverNewsRank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobNaverNewsRankConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TaskletNaverNewsRank taskletNaverNewsRank;

    @Bean
    public Job jobNaverNewsRank() {
        return jobBuilderFactory.get("jobNaverNewsRank")
            .start(stepNaverNewsRank())
            .build();
    }

    @Bean
    public Step stepNaverNewsRank() {
        DefaultTransactionAttribute transactionAttribute = new DefaultTransactionAttribute();
        // PROPAGATION_NEVER: 트랜잭션 없을 때만 작업이 진행되어야 할 때, 중첩된 트랜잭션을 방지하기 위해 사용
        transactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER);
        return stepBuilderFactory.get("stepNaverNewsRank").tasklet(taskletNaverNewsRank).transactionAttribute(transactionAttribute).build();
    }
}
