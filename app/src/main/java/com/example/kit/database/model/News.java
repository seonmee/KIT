package com.example.kit.database.model;

public class News {
    public static final String TABLE_NAME = "news";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_IMG = "image";
    public static final String COLUMN_DES = "des";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private String title;
    private String url;
    private String img;
    private String des;
    private String key;
    private String timestamp;



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_TITLE + " VARCHAR,"
                    + COLUMN_URL + " VARCHAR,"
                    + COLUMN_IMG + " VARCHAR,"
                    + COLUMN_DES + " VARCHAR,"
                    + COLUMN_KEY + " VARCHAR,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public News() {
    }

    public News(String title, String url, String img, String des, String key, String timestamp) {
        this.title = title;
        this.url = url;
        this.img = img;
        this.des = des;
        this.key = key;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
