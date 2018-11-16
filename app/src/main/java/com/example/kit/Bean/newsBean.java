package com.example.kit.Bean;

import java.io.Serializable;

public class newsBean implements Serializable {
    private String title;
    private String urlToImage;
    private String content;
    private String url;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }
}
