package org.oneandone.tech.poker.leo.data;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.IntConsumer;

public class MedianIntConsumer implements IntConsumer {

    private Queue<Integer> min = new PriorityQueue<>();

    private Queue<Integer> max = new PriorityQueue<>(Comparator.reverseOrder());

    private int count = 0;

    @Override
    public void accept(int value) {
        max.add(value);
        if ((count & 1) == 1) {
            min.add(max.poll());
        } else {
            if (min.isEmpty()) {

            } else if (max.peek() > min.peek()) {
                Integer maxV = max.poll();
                Integer minV = min.poll();
                max.add(minV);
                min.add(maxV);
            }
        }
        count++;
    }

    public double getMedian() {
        if ((count & 1) == 1) {
            return max.peek().doubleValue();
        } else {
            return (max.peek().doubleValue() + min.peek().doubleValue()) / 2;
        }
    }
}
