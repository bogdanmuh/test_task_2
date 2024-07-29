package org.example.aggregator;

import org.codehaus.jackson.map.ObjectMapper;
import org.example.aggregator.data.DataUrl;
import org.example.aggregator.data.FinalData;

import java.net.http.HttpClient;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static org.example.aggregator.Generator.generator;

public class ProccesserToken extends Thread {
    //Todo два разных запроса к сервису и какие то id камер будут одинаковы

    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run() {
        System.out.println("ProccesserToken запущен");
        while (true) {
            DataUrl data;
            try {
                data = TokenDataUrlQueue.poll();
            } catch (Throwable  e) {
                System.out.println(e.getMessage());
                System.out.println(e);
                throw new RuntimeException(e);
            }
            System.out.println("ProccesserToken count - "  + data.getCount() + " id - " + data.getId());

            //Todo Аналог http запроса  (нет доступа)
            CompletableFuture asd = CompletableFuture.runAsync(() -> {
                        System.out.println("sleep");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("+++++++++++++++++++++++++");
                    }
            ).thenAccept((a) ->{
                FinalData finalData = new FinalData (data.getId(), generator(), new Random().nextInt(100,400));
                try {
                    AggregatorSelector.add(data.getCount(), finalData);
                } catch (Throwable  e) {
                    System.out.println(e.getMessage());
                    System.out.println(e);
                    throw new RuntimeException(e);
                }
            });
            //end TODO
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

}
