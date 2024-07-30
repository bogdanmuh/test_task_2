package org.example.aggregator;

import org.example.aggregator.data.Data;
import org.example.aggregator.data.FinalData;
import org.example.aggregator.data.UrlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.aggregator.Generator.generator;

public class AggregatorService {

    private static final Logger log = LoggerFactory.getLogger(Aggregator.class.getName());
    private static final ConcurrentHashMap<Long, Object> locks = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, Aggregator> map = new ConcurrentHashMap<>();
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
        log.info("ServiceAgregator create");
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
        locks.put(c, new Object());
        map.put(c, new Aggregator(size, c, getLock(c)));

        for (int i = 0; i < size; i++) {
            sendRequestSource(c, i, data.get(i).getSourceDataUrl());
            //sourceDataUrlQueue.add(new DataUrl(c, i, data.get(i).getSourceDataUrl()));
            sendRequestToken(c, i, data.get(i).getTokenDataUrl());
            //tokenDataUrlQueue.add(new DataUrl(c, i, data.get(i).getTokenDataUrl()));
        }
        if (!map.get(c).isFull()) {
            log.info("ожидание аггрегации");
            Object lock = getLock(c);
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    log.error(String.valueOf(e));
                    throw new RuntimeException(e);
                }
            }
            locks.remove(c);
        }
        log.info("аггрегации завершена");
        return map.remove(c).getFinalData();
    }

    private void sendRequestSource(long count, int id, String dataUrl) {
        log.info("sendRequestSource count - {} id - {}", count, id);
        //Todo Аналог http запроса  (нет доступа)
        CompletableFuture.runAsync(() -> {
                    log.debug("ожидание ответа");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.debug("ответ пришел");
                }
        ).thenAccept((a) -> {
            UrlType urlType = new Random().nextInt(2) == 1 ? UrlType.ARCHIVE : UrlType.LIVE;
            FinalData finalData = new FinalData(id, urlType, generator());
            try {
                addNewData(count, finalData);
            } catch (Throwable e) {
                log.error(e.getMessage());
                log.error(String.valueOf(e));
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

    private void sendRequestToken(long count, int id, String dataUrl) {

        log.info("sendRequestToken count - {} id - {}", count, id);

        //Todo Аналог http запроса  (нет доступа)
        CompletableFuture.runAsync(() -> {
                    log.debug("ожидание ответа");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    log.debug("ответ пришел");

                }
        ).thenAccept((a) -> {
            FinalData finalData = new FinalData(id, generator(), new Random().nextInt(100, 400));
            try {
                addNewData(count, finalData);
            } catch (Throwable e) {
                log.error(e.getMessage());
                log.error(String.valueOf(e));
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

    public static void setSize(int size) {
        AggregatorService.size = size;
    }

    private Object getLock(Long count) {
        return locks.get(count);
    }

    private void addNewData(Long count, FinalData data) {
        map.get(count).addNewData(data);
    }

}


