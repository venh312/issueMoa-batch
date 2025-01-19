package com.issuemoa.batch.infrastructure.job.configs;

import com.issuemoa.batch.domain.prodcut.price.ProductPrice;
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
public class JobProductPriceConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobProductPrice(Step stepProductPrice) {
        return jobBuilderFactory.get("jobProductPrice")
                .incrementer(new RunIdIncrementer())
                .start(stepProductPrice)
                .build();
    }

    @Bean
    public Step stepProductPrice(ItemReader<NodeList> productPriceReader,
                            ItemProcessor<NodeList, List<ProductPrice>> productPriceProcessor,
                            ItemWriter<List<ProductPrice>> productPriceWriter) {
        return stepBuilderFactory.get("stepProductPrice")
                .<NodeList, List<ProductPrice>>chunk(1)
                .reader(productPriceReader)
                .processor(productPriceProcessor)
                .writer(productPriceWriter)
                .build();
    }
}
