package com.issuemoa.batch.infrastructure.job.configs;

import com.issuemoa.batch.infrastructure.job.tasklets.TaskletYoutubePopular;
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
public class JobYoutubePopularConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final TaskletYoutubePopular taskletYoutubePopular;

    @Bean
    public Job jobYoutubePopular() {
        return jobBuilderFactory.get("jobYoutubePopular")
            .start(stepYoutubePopular())
            .build();
    }

    @Bean
    public Step stepYoutubePopular() {
        DefaultTransactionAttribute transactionAttribute = new DefaultTransactionAttribute();
        transactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_NEVER);
        return stepBuilderFactory.get("stepYoutubePopular").tasklet(taskletYoutubePopular).transactionAttribute(transactionAttribute).build();
    }
}
