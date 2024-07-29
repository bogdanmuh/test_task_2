package org.example;

import org.example.aggregator.AggregatorService;
import org.example.aggregator.data.FinalData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ServiceAgregatorTest {
    @Test
    public void test100()  {
        AggregatorService serviceAgregator = AggregatorService.getInstance();
        AggregatorService.setSize(100);
        java.util.List<FinalData> a =  serviceAgregator.aggregareData();
        System.out.println(a);
        Assertions.assertEquals(a.size(), 100);
    }

    @Test
    public void test1000() {
        AggregatorService serviceAgregator = AggregatorService.getInstance();
        AggregatorService.setSize(1000);
        java.util.List<FinalData> a =  serviceAgregator.aggregareData();
        System.out.println(a);
        Assertions.assertEquals(a.size(), 1000);
    }


    @Test
    public void test10() {
        AggregatorService serviceAgregator = AggregatorService.getInstance();
        AggregatorService.setSize(10);
        java.util.List<FinalData> a =  serviceAgregator.aggregareData();
        System.out.println(a);
        Assertions.assertEquals(a.size(), 10);
    }

}