package com.example.kit.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;
import android.util.Log;
import android.widget.Toast;

import com.example.kit.Bean.ScrapNewsBean;

import java.util.ArrayList;
import java.util.List;

/*
 * by Seonmi Hyeon
 *
 * */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scrap_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(scrapDB.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + scrapDB.TABLE_NAME);
        onCreate(db);
    }

    public void delete(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String mTitle = title;

        db.execSQL("DELETE FROM " + scrapDB.TABLE_NAME
                + " WHERE " + scrapDB.COLUMN_TITLE
                + " = " + "\"" + title + "\""
                + ";");
    }

    /* id와 timestamps는 자동으로 삽입됨 */
    /* 북마크를 클릭시 추가 */
    public void createScrab (String keyword, String title, String url) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(scrapDB.COLUMN_KEYWORD, keyword);
        values.put(scrapDB.COLUMN_TITLE, title);
        values.put(scrapDB.COLUMN_URL,url);

        db.insert(scrapDB.TABLE_NAME, null, values);

    }

    /* Table에서 keyword 추출 */
    public List<String> getKeyword () {

        List<String> keywords = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

       /* Cursor cursor = db.query(
                scrapDB.TABLE_NAME,
                new String []{scrapDB.COLUMN_KEYWORD},
                null,
                null,
                null,
                null,
                null
        );*/
        Cursor cursor = db.rawQuery("SELECT DISTINCT keyword FROM scrap",null);

        while (cursor.moveToNext()){
            String keyword = cursor.getString(
                    cursor.getColumnIndexOrThrow(scrapDB.COLUMN_KEYWORD));
            keywords.add(keyword);
        }

        cursor.close();
        return  keywords;
    }

    /* get 메모, 제목, 시간
     * use 키워드
     * */
    public List<ScrapNewsBean> getNewsItem (String keyword){

        List<ScrapNewsBean> scrapNews = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                scrapDB.TABLE_NAME,
                new String[]{ scrapDB.COLUMN_TITLE, scrapDB.COLUMN_MEMO,scrapDB.COLUMN_TIMESTAMP},
                scrapDB.COLUMN_KEYWORD + "=?",
                new String[]{String.valueOf(keyword)},
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(scrapDB.COLUMN_TITLE));
            String memo = cursor.getString(
                    cursor.getColumnIndexOrThrow(scrapDB.COLUMN_MEMO));
            ScrapNewsBean scrapNewsBean = new ScrapNewsBean(title,memo);
            scrapNews.add(scrapNewsBean);
        }
        cursor.close();
        return scrapNews;
    }

    /* DB에 메모 추가 (update) */
    public void setMemo (String memo, String title){

        SQLiteDatabase db = this.getWritableDatabase();

        String Memo = memo;
        ContentValues values = new ContentValues();
        values.put(scrapDB.COLUMN_MEMO,memo);

        String selection = scrapDB.COLUMN_TITLE  + "=?";
        String[] selectionArgs = {title};

        db.update(
                scrapDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    /* get 메모, 제목, 시간
     * use 제목
     * */
    public String getUrl (String title){
        SQLiteDatabase db = this.getReadableDatabase();
        String Title = title;
        String url = " ";

        Cursor cursor = db.query(
                scrapDB.TABLE_NAME,
                new String[]{scrapDB.COLUMN_URL},
                scrapDB.COLUMN_TITLE + "=?",
                new String[]{String.valueOf(title)},
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            url = cursor.getString(
                    cursor.getColumnIndexOrThrow(scrapDB.COLUMN_URL)
            );
        }

        cursor.close();
        return url;
    }

    /*
    *
    public boolean isTitle(String title){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                scrapDB.TABLE_NAME,
                new String[]{scrapDB.COLUMN_TITLE} ,
                scrapDB.COLUMN_TITLE + "=?" ,
                new String[]{String.valueOf(title)},
                null,
                null,
                null
        );

        if(cursor != null){
            return false;
        }else {
            return  true;
        }
    }
    * */

}
