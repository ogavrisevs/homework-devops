package com.neueda.homework.devops.service;

import com.neueda.homework.devops.dto.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StatisticsService {

    private Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    private AtomicReference<Statistics> statisticsRef;

    public StatisticsService() {
        statisticsRef = new AtomicReference<>(null);
    }

    public Statistics update(int value) {
        Statistics updated = statisticsRef.updateAndGet(statistics -> Optional.ofNullable(statistics)
            .map(s -> s.update(value))
            .orElse(new Statistics(value, value, value)));
        logger.info("Updated statistics: {}; {}", value, updated);
        return updated;
    }

    public Statistics getCurrent() {
        return Optional.ofNullable(statisticsRef.get())
            .orElse(new Statistics(0, 0, 0, 0));
    }
}
