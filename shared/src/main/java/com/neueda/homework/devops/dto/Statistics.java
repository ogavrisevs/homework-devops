package com.neueda.homework.devops.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Statistics {

    private final int min;
    private final int max;
    private final double average;
    private final int count;

    @JsonCreator
    public Statistics(@JsonProperty("min") int min,
                      @JsonProperty("max") int max,
                      @JsonProperty("average") double average,
                      @JsonProperty("count") int count) {
        this.min = min;
        this.max = max;
        this.average = average;
        this.count = count;
    }

    public Statistics(int min, int max, double average) {
        this(min, max, average, 1);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public double getAverage() {
        return average;
    }

    public int getCount() {
        return count;
    }

    public Statistics update(int value) {
        return new Statistics(
            Math.min(min, value),
            Math.max(max, value),
            (value + count * average) / (count + 1),
            count + 1
        );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("min", min)
            .add("max", max)
            .add("average", average)
            .add("count", count)
            .toString();
    }
}
