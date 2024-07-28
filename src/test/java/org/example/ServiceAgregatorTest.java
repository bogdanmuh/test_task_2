package org.example;

import org.example.aggregator.AggregatorService;
import org.example.aggregator.data.FinalData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ServiceAgregatorTest {
    @Test
    public void test100() throws InterruptedException {
        AggregatorService serviceAgregator = AggregatorService.getInstance();
        AggregatorService.setSize(100);
        java.util.List<FinalData> a =  serviceAgregator.aggregareData();
        System.out.println(a);
        Assertions.assertEquals(a.size(), 100);
    }

    @Test
    public void test1000() throws InterruptedException {
        AggregatorService serviceAgregator = AggregatorService.getInstance();
        AggregatorService.setSize(1000);
        java.util.List<FinalData> a =  serviceAgregator.aggregareData();
        System.out.println(a);
        Assertions.assertEquals(a.size(), 1000);
    }


    @Test
    public void test10() throws InterruptedException {
        AggregatorService serviceAgregator = AggregatorService.getInstance();
        AggregatorService.setSize(10);
        java.util.List<FinalData> a =  serviceAgregator.aggregareData();
        System.out.println(a);
        Assertions.assertEquals(a.size(), 10);
    }

    @Test
    public void fewInstance() throws InterruptedException {
        AggregatorService.setSize(100);
        for (int i = 0; i < 1; i++) {
            Task aa = new Task();
            aa.start();
        }

    }


}