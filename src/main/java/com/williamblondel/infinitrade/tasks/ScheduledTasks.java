package com.williamblondel.infinitrade.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRateString = "${get.prices.fixed-rate.in.milliseconds}")
    public void scheduleTaskWithFixedRate() {
        logger.info("Fixed Rate Task: Current Time - {}", formatter.format(LocalDateTime.now()));
    }

}
