package org.oneandone.tech.poker.leo.services;

public enum Choice {
    V1(1), V2(2), V3(3), V5(5), V8(8), V13(13), V20(20), V40(40), V100(100), COFFEE, QUESTIONMARK;

    private final boolean hasValue;
    private final int value;

    private Choice() {
        hasValue = false;
        value = 0;
    }

    Choice(int value) {
        this.value = value;
        hasValue = true;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public int getValue() {
        return value;
    }
}
