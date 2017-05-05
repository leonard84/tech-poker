package org.oneandone.tech.poker.leo.data

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class MedianIntConsumerTest extends Specification {
    @Subject
    MedianIntConsumer medianIntConsumer = new MedianIntConsumer()

    @Unroll
    def "median of #input is #median"(List<Integer> input, double median) {
        when:
        input.stream().mapToInt({ it.intValue() }).forEach(medianIntConsumer)

        then:
        medianIntConsumer.median == median

        where:
        input                                                                                | median
        [59, 65, 61, 62, 53, 55, 60, 70, 64, 56, 58, 58, 62, 62, 68, 65, 56, 59, 68, 61, 67] | 61
        [12, 3, 5]                                                                           | 5
        [3, 13, 7, 5, 21, 23, 39, 23, 40, 23, 14, 12, 56, 23, 29]                            | 23
        [3, 13, 7, 5, 21, 23, 23, 40, 23, 14, 12, 56, 23, 29]                                | 22
    }
}
