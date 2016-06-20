package com.neueda.homework.devops.service;

import com.neueda.homework.devops.dto.Event;
import com.neueda.homework.devops.dto.ServiceInfo;
import com.neueda.homework.devops.dto.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ConsumerController {

    @Value("${service.name}")
    private String serviceName;

    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(path = "/", method = GET)
    public ServiceInfo getInfo() {
        return new ServiceInfo(serviceName);
    }

    @RequestMapping(path = "/statistics", method = GET)
    public Statistics getStatistics() {
        return statisticsService.getCurrent();
    }

    @RequestMapping(path = "/event", method = POST)
    public Statistics postEvent(@RequestBody @Validated Event event) {
        return statisticsService.update(event.getValue());
    }

}
