package org.example.aggregator;

import org.example.aggregator.data.Data;
import org.example.aggregator.data.DataUrl;
import org.example.aggregator.data.FinalData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.aggregator.Generator.generator;

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
        Thread thread1 = new ProccesserToken();
        thread1.start();
        Thread thread = new ProccesserSource();
        thread.start();
    }

    public List<FinalData> aggregareData()  {
        List <Data> data = new ArrayList<>();
        /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(body -> {
                    try {
                        FinalData finalData = mapper.readValue(body, FinalData.class);
                        //Map<String, Object> mapping = new ObjectMapper().readValue(body, HashMap.class);
                        AggregatorSelector.add(data.getCount(), finalData);
                    } catch (Throwable  e) {
                        System.out.println(e.getMessage());
                        System.out.println(e);
                        throw new RuntimeException(e);
                    }
                });*/
        //Todo Аналог http запроса  (нет доступа)
        for (int i = 0; i < size; i++) {
            data.add(new Data(i, generator(), generator()));
        }
        // end
        final long c = count.getAndIncrement();
        int size = data.size();
        Lock.add(c);
        AggregatorSelector.addAggregator(c, size);
        for (int i = 0; i < size; i++) {
            SourceDataUrlQueue.add(new DataUrl(c, i , data.get(i).getSourceDataUrl()));
            TokenDataUrlQueue.add(new DataUrl(c, i , data.get(i).getTokenDataUrl()));
        }

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
            Lock.remove(c);
        }
        System.out.println("аггрегации завершена");
        return AggregatorSelector.getFinalData(c);
    }

    public static void setSize(int size) {
        AggregatorService.size = size;
    }

}
