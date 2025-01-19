package com.issuemoa.batch.presentation;

import com.issuemoa.batch.infrastructure.scheduler.Scheduler;
import com.issuemoa.batch.presentation.enums.BatchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SchedulerController {
    private final Scheduler scheduler;

    @GetMapping("/execute/{type}")
    public void executeScheduler(@PathVariable String type) {
        log.info("[SchedulerController.executeScheduler] ==> {}", type);
        if (BatchType.NEWS.getValue().equals(type))
            scheduler.startJobNaverNewsRank();
        else if (BatchType.YOUTUBE.getValue().equals(type))
            scheduler.startJobYoutubePopular();
        else if (BatchType.KEYWORD.getValue().equals(type))
            scheduler.startJobMakeKeyword();
        else if (BatchType.STORE.getValue().equals(type))
            scheduler.startJobStore();
        else if (BatchType.PRODUCT.getValue().equals(type))
            scheduler.startJobProduct();
        else if (BatchType.PRODUCT_PRICE.getValue().equals(type))
            scheduler.startJobProductPrice();
    }
}
