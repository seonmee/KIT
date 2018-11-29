package com.example.kit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kit.database.model.Keyword;
import com.example.kit.database.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "news_db";

    public NewsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(News.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + News.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNews(News news) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(News.COLUMN_TITLE, news.getTitle());
        values.put(News.COLUMN_URL, news.getUrl());
        values.put(News.COLUMN_DES, news.getDes());
        values.put(News.COLUMN_IMG, news.getImg());
        values.put(News.COLUMN_KEY, news.getKey());

        // insert row
        long id = db.insert(News.TABLE_NAME, null, values);
        // close db connection
        db.close();
        return id;
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + News.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setTitle(cursor.getString(cursor.getColumnIndex(news.COLUMN_TITLE)));
                news.setUrl(cursor.getString(cursor.getColumnIndex(news.COLUMN_URL)));
                news.setDes(cursor.getString(cursor.getColumnIndex(news.COLUMN_DES)));
                news.setImg(cursor.getString(cursor.getColumnIndex(news.COLUMN_IMG)));
                news.setKey(cursor.getString(cursor.getColumnIndex(news.COLUMN_KEY)));
                news.setTimestamp(cursor.getString(cursor.getColumnIndex(news.COLUMN_TIMESTAMP)));

                newsList.add(news);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return newsList;
    } //모든 키워드의 뉴스

    public List<News> getNews(String keyword) {
        List<News> newsList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + News.TABLE_NAME + " WHERE "+News.COLUMN_KEY+" =  '"+keyword+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setTitle(cursor.getString(cursor.getColumnIndex(news.COLUMN_TITLE)));
                news.setUrl(cursor.getString(cursor.getColumnIndex(news.COLUMN_URL)));
                news.setDes(cursor.getString(cursor.getColumnIndex(news.COLUMN_DES)));
                news.setImg(cursor.getString(cursor.getColumnIndex(news.COLUMN_IMG)));
                news.setKey(cursor.getString(cursor.getColumnIndex(news.COLUMN_KEY)));
                news.setTimestamp(cursor.getString(cursor.getColumnIndex(news.COLUMN_TIMESTAMP)));

                newsList.add(news);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return newsList;
    }//키워드에 해당되는 뉴스들

    public int getNewsCount() {
        String countQuery = "SELECT  * FROM " + News.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public News getNews() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(News.TABLE_NAME,
                new String[]{News.COLUMN_TITLE, News.COLUMN_URL, News.COLUMN_DES, News.COLUMN_IMG, News.COLUMN_KEY, News.COLUMN_TIMESTAMP},
                News.COLUMN_KEY + "=?",
                new String[]{String.valueOf("빅데이터")}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        News news = new News(
                cursor.getString(cursor.getColumnIndex(News.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(News.COLUMN_URL)),
                cursor.getString(cursor.getColumnIndex(News.COLUMN_DES)),
                cursor.getString(cursor.getColumnIndex(News.COLUMN_IMG)),
                cursor.getString(cursor.getColumnIndex(News.COLUMN_KEY)),
                cursor.getString(cursor.getColumnIndex(News.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return news;
    }

    public void deleteNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(News.TABLE_NAME, News.COLUMN_URL + " = ?",
                new String[]{String.valueOf(news.getUrl())});
        db.close();
    }

}
