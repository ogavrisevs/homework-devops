package com.neueda.homework.devops.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class Event {

    @NotEmpty
    private final String uuid;

    @NotNull
    private final LocalDateTime time;

    @NotNull
    private final Integer value;

    @JsonCreator
    public Event(@JsonProperty("uuid") String uuid,
                 @JsonProperty("time") LocalDateTime time,
                 @JsonProperty("value") Integer value) {
        this.uuid = uuid;
        this.time = time;
        this.value = value;
    }

    public Event(int value) {
        this(UUID.randomUUID().toString(), LocalDateTime.now(), value);
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Integer getValue() {
        return value;
    }
}
