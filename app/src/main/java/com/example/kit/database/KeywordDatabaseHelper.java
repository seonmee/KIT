package com.example.kit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kit.database.model.Keyword;

import java.util.ArrayList;
import java.util.List;


public class KeywordDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "keywords_db";

    public KeywordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Keyword.CREATE_TABLE);

        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (1, '빅데이터', 'bigdata',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (2,'블록체인','blockchain',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (3,'인공지능','AI',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (4,'사물인터넷','IoT',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (5,'자율주행','self+driving',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (6,'가상현실','vr+OR+ar',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (7,'클라우드','cloud',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (8,'해킹','hacking',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (9,'백신','vaccine',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (10,'랜섬웨어','Ransomware',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (11,'딥러닝','deep+learning',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (12,'머신러닝','machine+learning',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (13,'챗봇','chatter+robot',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (14,'아이폰','iPhone',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (15,'안드로이드','android',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (16,'인터페이스','interface',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (17,'자동화','automation',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (18,'핀테크','Fintech',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (19,'드론','drone',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (20,'플랫폼','platform',0)");
        db.execSQL("INSERT INTO " + Keyword.TABLE_NAME + "(id, word, Tword, isSelected ) VALUES (21,'바이오','Bio',0)");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Keyword.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

//    public long insertKeyword(String keyword) {
//        // get writable database as we want to write data
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        // `id` and `timestamp` will be inserted automatically.
//        // no need to add them
//        values.put(Keyword.COLUMN_WORD, keyword);
//        values.put(Keyword.COLUMN_ISSELECTED, 1);
//        //switch-case문으로 Tword추가
//
//        // insert row
//        long id = db.insert(Keyword.TABLE_NAME, null, values);
//
//        // close db connection
//        db.close();
//        // return newly inserted row id
//        return id;
//    }

//    public Keyword getKeyword(long id) {
//        // get readable database as we are not inserting anything
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(Keyword.TABLE_NAME,
//                new String[]{Keyword.COLUMN_ID, Keyword.COLUMN_WORD, Keyword.COLUMN_TWORD, Keyword.COLUMN_ISSELECTED},
//                Keyword.COLUMN_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null, null, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        // prepare note object
//        Keyword keyword = new Keyword(
//                cursor.getInt(cursor.getColumnIndex(Keyword.COLUMN_ID)),
//                cursor.getString(cursor.getColumnIndex(Keyword.COLUMN_WORD)),
//                cursor.getString(cursor.getColumnIndex(Keyword.COLUMN_TWORD)),
//                cursor.getInt(cursor.getColumnIndex(Keyword.COLUMN_ISSELECTED)));
//
//        // close the db connection
//        cursor.close();
//
//        return keyword;
//    }

    public List<Keyword> getAllWords() {
        //Keyword keywords [] = new Keyword[34];
        //Keyword keywords [] = new Keyword[1];
        List<Keyword> keywords = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Keyword.TABLE_NAME +" ORDER BY " +
                Keyword.COLUMN_ID + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Keyword keyword = new Keyword();
                keyword.setId(cursor.getInt(cursor.getColumnIndex(Keyword.COLUMN_ID)));
                keyword.setWord(cursor.getString(cursor.getColumnIndex(Keyword.COLUMN_WORD)));
                keyword.setTword(cursor.getString(cursor.getColumnIndex(Keyword.COLUMN_TWORD)));
                keyword.setIsSelected(cursor.getInt(cursor.getColumnIndex(Keyword.COLUMN_ISSELECTED)));

                keywords.add(keyword);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return keywords;
    }//모든단어들

    public List<Keyword> getAllKeywords() {
        List<Keyword> keywords = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Keyword.TABLE_NAME + " WHERE "+Keyword.COLUMN_ISSELECTED+" = 1 "+" ORDER BY " +
                Keyword.COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Keyword keyword = new Keyword();
                keyword.setId(cursor.getInt(cursor.getColumnIndex(Keyword.COLUMN_ID)));
                keyword.setWord(cursor.getString(cursor.getColumnIndex(Keyword.COLUMN_WORD)));
                keyword.setTword(cursor.getString(cursor.getColumnIndex(Keyword.COLUMN_TWORD)));
                keyword.setIsSelected(cursor.getInt(cursor.getColumnIndex(Keyword.COLUMN_ISSELECTED)));

                keywords.add(keyword);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return keywords;
    }//선택된모든키워드들

    public int getKeywordsCount() {
        String countQuery = "SELECT  * FROM " + Keyword.TABLE_NAME + " WHERE "+Keyword.COLUMN_ISSELECTED+" = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int selectKeyword(String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Keyword.COLUMN_ISSELECTED, 1);

        // updating row
        return db.update(Keyword.TABLE_NAME, values, Keyword.COLUMN_WORD + " = ?",
                new String[]{String.valueOf(keyword)});
    }

    public int unselectKeyword(String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Keyword.COLUMN_ISSELECTED, 0);

        // updating row
        return db.update(Keyword.TABLE_NAME, values, Keyword.COLUMN_WORD + " = ?",
                new String[]{String.valueOf(keyword)});
    }
}
