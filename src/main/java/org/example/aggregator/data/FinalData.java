package org.example.aggregator.data;

public class FinalData {
    long id;
    UrlType urlType;
    String videoUrl;
    String value;
    int ttl;// int хватит?

    public FinalData(long id, String value, int ttl) {
        this.id = id;
        this.value = value;
        this.ttl = ttl;
    }

    public FinalData(long id, UrlType urlType, String videoUrl) {
        this.id = id;
        this.urlType = urlType;
        this.videoUrl = videoUrl;
    }

    public long getId() {
        return id;
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getValue() {
        return value;
    }

    public int getTtl() {
        return ttl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
