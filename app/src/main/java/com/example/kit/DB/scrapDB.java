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
    public static String COLUMN_URL = "url";
    public static final String COLUMN_ISCHECK = "ischeck";

    /* Create table */

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_KEYWORD +" TEXT NOT NULL,"
                    + COLUMN_TITLE  +" TEXT NOT NULL,"
                    + COLUMN_MEMO + " TEXT,"
                    + COLUMN_URL + " TEXT,"
                    + COLUMN_ISCHECK + " INTEGER DEFAULT 0"
                    + ")";


}
