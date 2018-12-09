package com.example.kit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kit.Adapter.NewsAdapter;
import com.example.kit.Bean.listBean;
import com.example.kit.Bean.newsBean;
import com.example.kit.database.ENewsDatabaseHelper;
import com.example.kit.database.KeywordDatabaseHelper;
import com.example.kit.database.NewsDatabaseHelper;
import com.example.kit.database.model.ENews;
import com.example.kit.database.model.Keyword;
import com.example.kit.database.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Splash extends Activity {

    /** 로딩 화면이 떠있는 시간(밀리초단위)  **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private KeywordDatabaseHelper keywordDB;
    private NewsDatabaseHelper newsDB;
    private ENewsDatabaseHelper enewsDB;
    private long now;
    String time;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    /** 처음 액티비티가 생성될때 불려진다. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.background_splash);
        setting = getSharedPreferences("setting", 0);
        time = setting.getString("time",null);
        newsDB = new NewsDatabaseHelper(this.getApplicationContext());
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = sdfNow.format(date);
        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");

        Log.d("time1 : ",formatDate);
        Log.d("time2 : ",fmtOut.format(date));

        if(time != null && newsDB.getNewsCount()>0 && !(fmtOut.format(date)).equals(time)){
            newsDB.deleteNews();
            enewsDB.deleteNews();
        }
        editor = setting.edit();
        editor.putString("time", fmtOut.format(date));
        editor.commit();

        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

}