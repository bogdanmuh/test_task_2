package org.example.aggregator.data;

public class DataUrl {
    long count;
    int id;
    String dataUrl;

    public DataUrl(long count, int id, String dataUrl) {
        this.count = count;
        this.id = id;
        this.dataUrl = dataUrl;
    }

    public long getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public String getDataUrl() {
        return dataUrl;
    }
}
