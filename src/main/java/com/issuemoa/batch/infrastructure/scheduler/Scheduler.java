package com.issuemoa.batch.infrastructure.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableScheduling
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final Job jobNaverNewsRank;
    private final Job jobYoutubePopular;
    private final Job jobMakeKeyword;
    private final Job jobStore;
    private final Job jobProduct;
    private final Job jobProductPrice;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "${cron.expression.naverNewsRank}")
    public void startJobNaverNewsRank() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("requestDate", new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            log.info("[Scheduler 실행 => JobNaverNewsRank]");
            jobLauncher.run(jobNaverNewsRank, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron = "${cron.expression.youtubePopular}")
    public void startJobYoutubePopular() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("requestDate", new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            log.info("[Scheduler 실행 => JobYoutubePopular]");
            jobLauncher.run(jobYoutubePopular, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(cron = "${cron.expression.keyword}")
    public void startJobMakeKeyword() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("requestDate", new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            log.info("[Scheduler 실행 => JobMakeKeyword]");
            jobLauncher.run(jobMakeKeyword, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error(e.getMessage());
        }
    }

    public void startJobStore() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("requestDate", new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            log.info("[Scheduler 실행 => JobStore]");
            jobLauncher.run(jobStore, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error(e.getMessage());
        }
    }

    public void startJobProduct() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("requestDate", new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            log.info("[Scheduler 실행 => JobProduct]");
            jobLauncher.run(jobProduct, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error(e.getMessage());
        }
    }

    public void startJobProductPrice() {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        jobParameterMap.put("requestDate", new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(jobParameterMap);

        try {
            log.info("[Scheduler 실행 => JobProductPrice]");
            jobLauncher.run(jobProductPrice, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            log.error(e.getMessage());
        }
    }
}
