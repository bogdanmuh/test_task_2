package org.example.aggregator;

import org.example.aggregator.data.DataUrl;
import org.example.aggregator.data.FinalData;

import java.util.Random;

public class ProccesserToken extends Thread {

    /*два разных к сервису и какие id камер будут одинаковы*/



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
            /*обращаюсь к чему то пока генератор */
            FinalData finalData;
            if (isCome()) {
                finalData = new FinalData (data.getId(), generator(), new Random().nextInt(100,400));
            } else {
                finalData = new FinalData (data.getId(), "", -1);
            }
            // end
            try {
                AggregatorSelector.add(data.getCount(), finalData);
            } catch (Throwable  e) {
                System.out.println(e.getMessage());
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
    }
    public boolean isCome() {
        return true;
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
