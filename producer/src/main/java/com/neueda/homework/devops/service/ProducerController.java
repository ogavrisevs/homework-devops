package com.neueda.homework.devops.service;

import com.neueda.homework.devops.dto.ServiceInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProducerController {

    @Value("${service.name}")
    private String serviceName;

    @RequestMapping(path = "/", method = GET)
    public ServiceInfo getInfo() {
        return new ServiceInfo(serviceName);
    }

}
