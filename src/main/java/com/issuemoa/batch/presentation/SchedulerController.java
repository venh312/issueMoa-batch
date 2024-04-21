package com.issuemoa.batch.presentation;

import com.issuemoa.batch.infrastructure.scheduler.Scheduler;
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
        log.info("[배치 실행 SchedulerController] => {}", type);
        if (type.equals("news")) {
            scheduler.startJobNaverNewsRank();
        } else if (type.equals("youtube")) {
            scheduler.startJobYoutubePopular();
        }
    }
}
