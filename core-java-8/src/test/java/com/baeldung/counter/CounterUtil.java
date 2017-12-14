<<<<<<< HEAD
package com.baeldung.counter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CounterUtil {

    private final static String[] COUNTRY_NAMES = { "China", "Australia", "India", "USA", "USSR", "UK", "China", "France", "Poland", "Austria", "India", "USA", "Egypt", "China" };

    public static void counterWithWrapperObject(Map<String, Integer> counterMap) {
        for (String country : COUNTRY_NAMES) {
            counterMap.compute(country, (k, v) -> v == null ? 1 : v + 1);
        }
    }

    public static void counterWithLambdaAndWrapper(Map<String, Long> counterMap) {
        counterMap.putAll(Stream.of(COUNTRY_NAMES)
            .parallel()
            .collect(Collectors.groupingBy(k -> k, Collectors.counting())));
    }

    public static class MutableInteger {
        int count;

        public MutableInteger(int count) {
            this.count = count;
        }

        public void increment() {
            this.count++;
        }

        public int getCount() {
            return this.count;
        }
    }

    public static void counterWithMutableInteger(Map<String, MutableInteger> counterMap) {
        for (String country : COUNTRY_NAMES) {
            counterMap.compute(country, (k, v) -> v == null ? new MutableInteger(0) : v)
                .increment();
        }
    }

    public static void counterWithPrimitiveArray(Map<String, int[]> counterMap) {
        for (String country : COUNTRY_NAMES) {
            counterMap.compute(country, (k, v) -> v == null ? new int[] { 0 } : v)[0]++;
        }
    }

}
=======
package com.baeldung.counter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CounterUtil {

    private final static String[] COUNTRY_NAMES = { "China", "Australia", "India", "USA", "USSR", "UK", "China", "France", "Poland", "Austria", "India", "USA", "Egypt", "China" };

    public static void counterWithWrapperObject(Map<String, Integer> counterMap) {
        for (String country : COUNTRY_NAMES) {
            counterMap.compute(country, (k, v) -> v == null ? 1 : v + 1);
        }
    }

    public static void counterWithLambdaAndWrapper(Map<String, Long> counterMap) {
        counterMap.putAll(Stream.of(COUNTRY_NAMES)
            .parallel()
            .collect(Collectors.groupingBy(k -> k, Collectors.counting())));
    }

    public static class MutableInteger {
        int count;

        public MutableInteger(int count) {
            this.count = count;
        }

        public void increment() {
            this.count++;
        }

        public int getCount() {
            return this.count;
        }
    }

    public static void counterWithMutableInteger(Map<String, MutableInteger> counterMap) {
        for (String country : COUNTRY_NAMES) {
            counterMap.compute(country, (k, v) -> v == null ? new MutableInteger(0) : v)
                .increment();
        }
    }

    public static void counterWithPrimitiveArray(Map<String, int[]> counterMap) {
        for (String country : COUNTRY_NAMES) {
            counterMap.compute(country, (k, v) -> v == null ? new int[] { 0 } : v)[0]++;
        }
    }

}
>>>>>>> 7a4e60a49ab53bd7e2f3be495e7e03d412054f82
