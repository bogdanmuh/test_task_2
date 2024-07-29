package org.example.aggregator;

import org.codehaus.jackson.map.ObjectMapper;
import org.example.aggregator.data.DataUrl;
import org.example.aggregator.data.FinalData;
import org.example.aggregator.data.UrlType;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class ProccesserSource extends Thread {

    /*два разных запроса к сервису и какие то id камер будут одинаковы */
    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void run() {
        System.out.println("ProccesserSource - запущен" );
        while (true) {
            DataUrl data;
            try {
                data = SourceDataUrlQueue.poll();
            } catch (Throwable e) {
                System.out.println(e.getMessage());
                System.out.println(e);
                throw new RuntimeException(e);
            }
            System.out.println("ProccesserSource count - "  + data.getCount() + " id - " + data.getId());

            /*CompletableFuture.runAsync(() -> {
                        System.out.println("sleep");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("+++++++++++++++++++++++++");
                    }
            ).thenAccept((a) ->{
                UrlType urlType = new Random().nextInt(2) == 1 ? UrlType.ARCHIVE : UrlType.LIVE;
                FinalData finalData = new FinalData (data.getId(), urlType, generator());
                try {
                    AggregatorSelector.add(data.getCount(), finalData);
                } catch (Throwable  e) {
                    System.out.println(e.getMessage());
                    System.out.println(e);
                    throw new RuntimeException(e);
                }
            });*/

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(data.getDataUrl()))
                    .GET()
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
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
                    });

        }
    }
    public String generator () {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 8; i++) {
            int index = new Random().nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
