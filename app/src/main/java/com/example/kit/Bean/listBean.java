package com.example.kit.Bean;

import java.io.Serializable;

public class listBean implements Serializable {
    private String url;
    private String keyword;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
