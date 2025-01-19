package com.issuemoa.batch.infrastructure.job.configs;

import com.issuemoa.batch.domain.prodcut.Product;
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

@RequiredArgsConstructor
@Configuration
public class JobProductConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobProduct(Step stepProduct) {
        return jobBuilderFactory.get("jobProduct")
                .incrementer(new RunIdIncrementer())
                .start(stepProduct)
                .build();
    }

    @Bean
    public Step stepProduct(ItemReader<NodeList> productReader,
                            ItemProcessor<NodeList, List<Product>> productProcessor,
                            ItemWriter<List<Product>> productWriter) {
        return stepBuilderFactory.get("stepProduct")
                .<NodeList, List<Product>>chunk(1)
                .reader(productReader)
                .processor(productProcessor)
                .writer(productWriter)
                .build();
    }
}
