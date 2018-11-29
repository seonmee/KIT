package com.example.kit.database.model;

/**
 * Created by ravi on 20/02/18.
 */

public class Keyword {
    public static final String TABLE_NAME = "keywords";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_TWORD = "Tword";
    public static final String COLUMN_ISSELECTED = "isSelected";

    private int id;
    private int isSelected;
    private String word;
    private String Tword;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_WORD + " VARCHAR,"
                    + COLUMN_TWORD + " VARCHAR,"
                    + COLUMN_ISSELECTED + " INTEGER"
                    + ")";

    public Keyword() {
    }

    public Keyword(int id, String word, String Tword, int isSelected) {
        this.id = id;
        this.word = word;
        this.Tword = Tword;
        this.isSelected = isSelected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTword() {
        return Tword;
    }

    public void setTword(String tword) {
        Tword = tword;
    }
}
