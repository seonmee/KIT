package com.example.kit.Bean;

public class ScrapNewsBean {
    private String mTitle;
    private String mMemo;

    public ScrapNewsBean (String title, String memo) {
        mTitle = title;
        mMemo = memo;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmMemo(String mMemo) {
        this.mMemo = mMemo;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmMemo() {
        return mMemo;
    }
}
