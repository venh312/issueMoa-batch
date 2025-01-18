package com.issuemoa.batch.infrastructure.job.configs;

import com.issuemoa.batch.domain.store.Store;
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
import org.w3c.dom.NodeList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class JobStoreConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobStore(Step stepStore) {
        return jobBuilderFactory.get("jobStore")
                .incrementer(new RunIdIncrementer())
                .start(stepStore)
                .build();
    }

    @Bean
    public Step stepStore(ItemReader<NodeList> storeReader,
                            ItemProcessor<NodeList, List<Store>> storeProcessor,
                            ItemWriter<List<Store>> storeWriter) {
        return stepBuilderFactory.get("stepStore")
                .<NodeList, List<Store>>chunk(1)
                .reader(storeReader)
                .processor(storeProcessor)
                .writer(storeWriter)
                .build();
    }
}
