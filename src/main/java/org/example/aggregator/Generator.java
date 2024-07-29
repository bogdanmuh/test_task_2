package org.example.aggregator;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Generator {

    public static String generator () {
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
