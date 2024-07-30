package org.example.aggregator;

import org.example.aggregator.data.FinalData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Aggregator {
    private static final Logger log = LoggerFactory.getLogger(Aggregator.class.getName());

    private final ConcurrentHashMap<Long, FinalData> map = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<FinalData> arrayList;
    private final Object lock;
    private final int size;
    private final long count;

    public Aggregator(int size, long count, Object lock) {
        this.size = size;
        this.count = count;
        arrayList = new CopyOnWriteArrayList<>();
        this.lock = lock;
    }

    public void addNewData(FinalData data) {
        log.info("add to Array count - {}", count);
        long id = data.getId();
        FinalData mapData = map.get(id);
        if (mapData == null) {
            map.put(id, data);
        } else {
            int ttl = data.getTtl();
            if (ttl == 0 && data.getValue() == null) {
                data.setTtl(ttl);
                data.setValue(null);
            } else {
                data.setUrlType(mapData.getUrlType());
                data.setVideoUrl(mapData.getVideoUrl());
            }
            arrayList.add(data);
            map.remove(id);
            log.debug("lock");
            System.out.println();
            synchronized (lock) {
                if (arrayList.size() == size) {
                    log.debug("Array count - {} ready ", count);
                    System.out.println();
                    lock.notify();
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
/*
* По ТЗ отметил следующее:
*
- При одновременном вызове /aggregator двумя клиентами получим race condition.
- Использование System.out.println() вместо фасадов логирования.
- Коммиты с одинаковым сообщением init не дают представления о том, какие изменения вносились.
*/