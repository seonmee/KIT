package com.example.kit.DB;

/*
 * by Seonmi Hyeon
 *
 * */

public class scrapDB {

    public static final String TABLE_NAME = "scrap";

    public static String COLUMN_ID = "id";
    public static String COLUMN_KEYWORD = "keyword";
    public static String COLUMN_TITLE = "title";
    public static String COLUMN_MEMO = "memo";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int mId;
    private String mKeyword;
    private String mTitle;
    private String mMemo;
    private String mTimestamp;

    /* Create table */

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_KEYWORD +" TEXT NOT NULL,"
            + COLUMN_TITLE  +" TEXT NOT NULL,"
            + COLUMN_MEMO + " TEXT,"
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    public scrapDB (){

    }

    public scrapDB (int id, String keyword, String title) {
        mId = id;
        mKeyword = keyword;
        mTitle = title;
    }

    public int getmId() {
        return mId;
    }

    public String getmKeyword() {
        return mKeyword;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmMemo() {
        return mMemo;
    }

    public String getmTimestamp() {
        return mTimestamp;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmKeyword(String mKeyword) {
        this.mKeyword = mKeyword;
    }

    public void setmMemo(String mMemo) {
        this.mMemo = mMemo;
    }

    public void setmTimestamp(String mTimestamp) {
        this.mTimestamp = mTimestamp;
    }
}
