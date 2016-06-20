package com.neueda.homework.devops.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.neueda.homework.devops.dto.RegistryNode;
import com.neueda.homework.devops.dto.RegistryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class RegistryClient {

    private final Logger logger = LoggerFactory.getLogger(RegistryClient.class);

    @Autowired
    private RegistryResource registryResource;

    @Autowired
    @Qualifier("endpoint")
    private Cache<String, String> endpointCache;

    @Autowired
    @Qualifier("resource")
    private Cache<String, Object> resourceCache;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${service.name}")
    private String serviceName;

    @Value("${server.address:127.0.0.1}")
    private String host;

    @Value("${server.port}")
    private int port;

    @Scheduled(fixedDelay = 1000)
    void heartbeat() {
        boolean failed = false;
        try {
            Response<RegistryResponse> response =
                registryResource.register(serviceName, serviceEndpoint(), 5).execute();
            if (!response.isSuccessful()) {
                logger.error("Heartbeat for {} failed: {} {}", serviceName, response.code(), response.message());
            }
        } catch (IOException e) {
            logger.error("Heartbeat for {} failed: {}", serviceName, e.getMessage());
        }
    }

    /**
     * Any sufficiently complicated client-side discovery
     * contains an ad hoc, informally-specified, bug-ridden, slow implementation
     * of half of Netflix Ribbon.
     */
    public <T> Optional<T> discover(String serviceName, Class<T> resourceClass) {
        return discoverEndpoint(serviceName)
            .flatMap(endpoint -> getResource(endpoint, resourceClass));
    }

    private Optional<String> discoverEndpoint(String serviceName) {
        Optional<String> cachedEndpoint = Optional.ofNullable(endpointCache.getIfPresent(serviceName));
        if (cachedEndpoint.isPresent()) {
            return cachedEndpoint;
        }
        try {
            Response<RegistryResponse> response = registryResource.discover(serviceName).execute();
            return Optional.ofNullable(response.body())
                .map(RegistryResponse::getNode)
                .map(RegistryNode::getValue)
                .map(endpoint -> {
                    endpointCache.put(serviceName, endpoint);
                    return endpoint;
                });
        } catch (IOException e) {
            logger.error("Discovery for {} failed: {}", serviceName, e.getMessage());
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> getResource(String endpoint, Class<T> resourceClass) {
        try {
            Object resource = resourceCache.get(endpoint, () -> new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(resourceClass));
            return Optional.of((T) resource);
        } catch (ExecutionException e) {
            logger.error("Creating client for {} failed: {}", endpoint, e.getMessage());
        }
        return Optional.empty();
    }

    private String serviceEndpoint() {
        return String.format("http://%s:%d", host, port);
    }

}
