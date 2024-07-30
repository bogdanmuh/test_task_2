package org.example;

import org.apache.log4j.PropertyConfigurator;
import org.example.aggregator.AggregatorService;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        PropertyConfigurator.configure("C:\\Users\\Bogdan\\IdeaProjects\\demo1\\untitled\\logs\\log4j.properties");

        AggregatorService.setSize(100);
        //AggregatorService.getInstance().aggregareData();
        for (int i = 0; i < 10; i++) {
            Task aa = new Task();
            Task aaa = new Task();
            aa.start();
            aaa.start();
        }
    }

}