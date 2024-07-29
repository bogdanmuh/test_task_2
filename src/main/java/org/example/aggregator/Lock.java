package org.example.aggregator;

import java.util.concurrent.ConcurrentHashMap;

public class Lock {

    private static final ConcurrentHashMap<Long, Object> map = new ConcurrentHashMap<>();

    public static void add(Long count) {
        map.put(count, new Object());
    }

    public static Object get (Long count) {
        return map.get(count);
    }

    public static void remove (Long count) {
        map.remove(count);
    }

}
