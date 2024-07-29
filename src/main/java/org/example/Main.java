package org.example;

import org.example.aggregator.AggregatorService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        AggregatorService.setSize(100);
        //AggregatorService.getInstance().aggregareData();
        Long a = new Date().getTime();
        for (int i = 0; i < 10; i++) {
            Task aa = new Task();
            aa.start();
        }
        System.out.println(a - new Date().getTime());
    }
}