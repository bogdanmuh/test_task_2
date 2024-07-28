package org.example;

import org.example.aggregator.AggregatorService;

public class Main {
    public static void main(String[] args) {
        AggregatorService.setSize(100);
        for (int i = 0; i < 10; i++) {
            Task aa = new Task();
            aa.start();
        }


    }
}