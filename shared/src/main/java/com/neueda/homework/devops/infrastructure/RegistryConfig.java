package com.neueda.homework.devops.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
class RegistryConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${etcd}")
    private String etcdEndpoint;

    @Bean
    ResourceFactory resourceFactory() {
        return new ResourceFactory() {
            @Override
            public <T> T create(String endpoint, Class<T> resourceClass) {
                return new Retrofit.Builder()
                    .baseUrl(endpoint)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build()
                    .create(resourceClass);
            }
        };
    }

    @Bean
    RegistryResource registryResource(ResourceFactory resourceFactory) {
        return resourceFactory.create(etcdEndpoint, RegistryResource.class);
    }

    @Bean
    @Qualifier("endpoint")
    Cache<String, String> endpointCache() {
        return CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();
    }

    @Bean
    @Qualifier("resource")
    Cache<String, Object> resourceCache() {
        return CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .build();
    }

}
