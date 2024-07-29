package org.example.aggregator;

import org.example.aggregator.data.FinalData;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class AggregatorSelector {

    private static final ConcurrentHashMap<Long, Aggregator> map = new ConcurrentHashMap<>();

    public static void add (Long count, FinalData data) {map.get(count).add(data);}
    public static void addAggregator(long count, int size) {
        map.put(count, new Aggregator(size, count));
    }
    public static boolean isReady (long count) {
        return map.get(count).isFull();
     }
    public static List<FinalData> getFinalData (long count) {
        List<FinalData> lsit = map.get(count).getFinalData();
        map.remove(count);
        return lsit;
    }

}
