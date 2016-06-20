package com.neueda.homework.devops.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistryNode {

    private final String value;

    @JsonCreator
    public RegistryNode(@JsonProperty("value") String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
