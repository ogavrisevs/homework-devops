package com.neueda.homework.devops.service;

import com.neueda.homework.devops.dto.Event;
import com.neueda.homework.devops.infrastructure.ConsumerResource;
import com.neueda.homework.devops.infrastructure.RegistryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class EventService {

    @Autowired
    private RegistryClient registryClient;

    @Autowired
    @Qualifier("event")
    private Random random;

    @Scheduled(fixedDelay = 1000)
    void postValue() {
        int value = random.nextInt(100) + 1;
        registryClient.discover("consumer", ConsumerResource.class).ifPresent(resource -> {
            try {
                resource.postEvent(new Event(value)).execute();
            } catch (IOException ignored) {
            }
        });
    }

}
