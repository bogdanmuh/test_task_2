package org.example;

import org.example.aggregator.AggregatorService;
import org.example.aggregator.data.FinalData;

import java.util.Date;
import java.util.List;

public class Task extends java.lang.Thread {

    @Override
    public void run() {
        Long a = new Date().getTime();
        AggregatorService serviceAgregator = AggregatorService.getInstance();
        List<FinalData> s = serviceAgregator.aggregareData();
        if (s.size() == 100) {
            System.out.println("+++++++++++++++++++++++++");
        } else {
            System.out.println("--------------------------------------");
        }
        System.out.println("time - " + (a - new Date().getTime()));
    }

}

