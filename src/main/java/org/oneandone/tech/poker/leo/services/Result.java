package org.oneandone.tech.poker.leo.services;

import java.util.EnumMap;
import java.util.Map;

public class Result {
    private double average;
    private int min;
    private int max;
    private Map<Choice, Integer> votes = new EnumMap<>(Choice.class);

    public Result(double average, int min, int max, Map<Choice, Integer> votes) {
        this.average = average;
        this.min = min;
        this.max = max;
        this.votes.putAll(votes);
    }

    public double getAverage() {
        return average;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Map<Choice, Integer> getVotes() {
        return votes;
    }
}
