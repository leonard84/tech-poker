package org.oneandone.tech.poker.leo.services;

public class Result {
    private double average;
    private int min;
    private int max;

    public Result(double average, int min, int max) {
        this.average = average;
        this.min = min;
        this.max = max;
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
}
