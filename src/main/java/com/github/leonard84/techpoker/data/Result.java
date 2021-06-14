package com.github.leonard84.techpoker.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Result {
    private double average;
    private int min;
    private int max;
    private double median;

    private List<ChoiceResult> votes = new ArrayList<>();

    public Result(double average, int min, int max, double median, Collection<ChoiceResult> votes) {
        this.average = average;
        this.min = min;
        this.max = max;
        this.median = median;
        this.votes.addAll(votes);
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

    public double getMedian() {
        return median;
    }

    public List<ChoiceResult> getVotes() {
        return votes;
    }
}
