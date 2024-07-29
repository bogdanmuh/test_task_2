package org.example.aggregator;

import org.example.aggregator.data.FinalData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Aggregator {

    private final ConcurrentHashMap<Long, FinalData> map = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList <FinalData> arrayList;
    private final Object lock;
    private final int size;
    private final long count;

    public Aggregator(int size, long count) {
        this.size = size;
        this.count = count;
        arrayList = new CopyOnWriteArrayList<>();
        lock = Lock.get(count);

    }

    public void add (FinalData data) {
        System.out.println("add to Array " + count);
        FinalData mapData = map.get(data.getId());
        if (mapData == null) {
            map.put(data.getId(), data);
        } else {
            if (data.getTtl() == 0 && data.getValue() == null) {
                data.setTtl(mapData.getTtl());
                data.setValue(mapData.getValue());
            } else {
                data.setUrlType(mapData.getUrlType());
                data.setVideoUrl(mapData.getVideoUrl());
            }

            arrayList.add(data);
            map.remove(data.getId());
            synchronized (lock) {
                if (arrayList.size() == size) {
                    System.out.println("Array " + count + "ready ");
                    Lock.get(count).notify();
                }
            }
        }
    }

    public boolean isFull() {
        return arrayList.size() == size;
    }

    public List<FinalData> getFinalData() {
        return isFull() ? arrayList : new ArrayList<>();
    }

}
