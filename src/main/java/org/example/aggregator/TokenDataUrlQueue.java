package org.example.aggregator;

import org.example.aggregator.data.DataUrl;

import java.util.concurrent.LinkedBlockingDeque;

//подумать
public class TokenDataUrlQueue {
    private static LinkedBlockingDeque<DataUrl> queue = new LinkedBlockingDeque<>();

    public static void add(DataUrl data) {
        queue.add(data);
    }
    public static DataUrl poll() throws InterruptedException {
        return queue.take();
    }

}
