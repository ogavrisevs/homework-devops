package com.neueda.homework.devops.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ServiceInfo {

    @NotNull
    private final String name;

    @JsonCreator
    public ServiceInfo(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
