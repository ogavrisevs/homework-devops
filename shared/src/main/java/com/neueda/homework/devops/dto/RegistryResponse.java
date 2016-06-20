package com.neueda.homework.devops.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistryResponse {

    private final RegistryNode node;

    @JsonCreator
    public RegistryResponse(@JsonProperty("node") RegistryNode node) {
        this.node = node;
    }

    public RegistryNode getNode() {
        return node;
    }
}
