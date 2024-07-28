package org.example.aggregator.data;


public class Data {
    int id;
    String sourceDataUrl;
    String tokenDataUrl;

    public Data(int id, String sourceDataUrl, String tokenDataUrl) {
        this.id = id;
        this.sourceDataUrl = sourceDataUrl;
        this.tokenDataUrl = tokenDataUrl;
    }

    public int getId() {
        return id;
    }

    public String getSourceDataUrl() {
        return sourceDataUrl;
    }

    public String getTokenDataUrl() {
        return tokenDataUrl;
    }
}
