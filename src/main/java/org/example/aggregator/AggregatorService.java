package org.example.aggregator;

import org.example.aggregator.data.Data;
import org.example.aggregator.data.DataUrl;
import org.example.aggregator.data.FinalData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AggregatorService {
    private static final AtomicLong count = new AtomicLong();
    private static final String url = "https://run.mocky.io/v3/0f95b5e3-6dbf-4d18-8321-e21c9a4ef613";
    private static int size = 100;
    private static final class Holder {
        private static final AggregatorService INSTANCE = new AggregatorService();
    }
    public static AggregatorService getInstance() {
        return Holder.INSTANCE;
    }

    private AggregatorService() {
        System.out.println("ServiceAgregator create");
        // нескольок потоков
        Thread thread1 = new ProccesserToken();
        thread1.start();
        // нескольок потоков
        Thread thread = new ProccesserSource();
        thread.start();
    }

    public List<FinalData> aggregareData()  {
        System.out.println("aggregareData url - " + url);
        List <Data> data = new ArrayList<>();
        /*обращаюсь к чему то пока генератор */ //нет ответа ?
        for (int i = 0; i < size; i++) {
            data.add(new Data(i, generator(), generator()));
        }
        // end
        final long c = count.getAndIncrement();
        int size = data.size();
        AggregatorSelector.addAggregator(c, size);
        for (int i = 0; i < size; i++) {
            SourceDataUrlQueue.add(new DataUrl(c, i , data.get(i).getSourceDataUrl()));
            TokenDataUrlQueue.add(new DataUrl(c, i , data.get(i).getTokenDataUrl()));
        }
        Lock.add(c);
        if (!AggregatorSelector.isReady(c)) {
            System.out.println("ожидание аггрегации ");
            Object lock = Lock.get(c);
            synchronized(lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                    System.out.println(e);
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("аггрегации завершена");
        return AggregatorSelector.getFinalData(c);
    }

    private String generator () {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            int index = new Random().nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private boolean isUrl(String url) {
        return true;
    }

    public static void setSize(int size) {
        AggregatorService.size = size;
    }

}
