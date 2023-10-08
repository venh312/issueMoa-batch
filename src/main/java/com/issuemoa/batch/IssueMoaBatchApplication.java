package com.issuemoa.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing // 배치 활성화
@SpringBootApplication
public class IssueMoaBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(IssueMoaBatchApplication.class, args);
	}
}
