package org.example.aggregator;

import org.example.aggregator.data.Data;
import org.example.aggregator.data.FinalData;
import org.example.aggregator.data.UrlType;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

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
    }

    public List<FinalData> aggregareData() {
        List<Data> data = new ArrayList<>();
        /*HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> a;
        /*try {
             a = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(a);*/
        //Todo 404 ошибка
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
            a(c, i, data.get(i).getSourceDataUrl());
            //sourceDataUrlQueue.add(new DataUrl(c, i, data.get(i).getSourceDataUrl()));
            b(c, i, data.get(i).getTokenDataUrl());
            //tokenDataUrlQueue.add(new DataUrl(c, i, data.get(i).getTokenDataUrl()));
        }
        if (!AggregatorSelector.isReady(c)) {
            System.out.println("ожидание аггрегации");
            Object lock = Lock.get(c);
            synchronized (lock) {
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

    private void a (long count, int id, String dataUrl) {
        System.out.println("ProccesserSource count - " + count + " id - " + count);
        //Todo Аналог http запроса  (нет доступа)
        CompletableFuture.runAsync(() -> {
                    System.out.println("sleep");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("+++++++++++++++++++++++++");
                }
        ).thenAccept((a) -> {
            UrlType urlType = new Random().nextInt(2) == 1 ? UrlType.ARCHIVE : UrlType.LIVE;
            FinalData finalData = new FinalData(id, urlType, generator());
            try {
                AggregatorSelector.add(count, finalData);
            } catch (Throwable e) {
                System.out.println(e.getMessage());
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
        //end Todo
        //end Todo
            /*HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(data.getDataUrl()))
                    .GET()
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .async
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

    }

    private void b (long count, int id, String dataUrl) {
        System.out.println("ProccesserToken count - " + count+ " id - " + id);
        //Todo Аналог http запроса  (нет доступа)
        CompletableFuture.runAsync(() -> {
                    System.out.println("sleep");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("+++++++++++++++++++++++++");
                }
        ).thenAccept((a) -> {
            FinalData finalData = new FinalData(id, generator(), new Random().nextInt(100, 400));
            try {
                AggregatorSelector.add(count, finalData);
            } catch (Throwable e) {
                System.out.println(e.getMessage());
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
        /*HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(data.getDataUrl()))
                    .GET()
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(body -> {
                        try {
                            FinalData finalData = mapper.readValue(body, FinalData.class);
                            AggregatorSelector.add(data.getCount(), finalData);
                        } catch (Throwable  e) {
                            System.out.println(e.getMessage());
                            System.out.println(e);
                            throw new RuntimeException(e);
                        }
                    });*/
    }

}


