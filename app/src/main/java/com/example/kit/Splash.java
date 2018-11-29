package com.example.kit;

import android.app.Activity;
import android.content.Intent;
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
import com.example.kit.database.KeywordDatabaseHelper;
import com.example.kit.database.NewsDatabaseHelper;
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
    private long now;

    /** 처음 액티비티가 생성될때 불려진다. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.background_splash);

        if(Function.isNetworkAvailable(getApplicationContext()))
        {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        }else{
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
//                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        List<listBean> listbean = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {

            keywordDB = new KeywordDatabaseHelper(Splash.this);
            newsDB = new NewsDatabaseHelper(Splash.this);
            //Keyword wordArray [] = keywordDB.getAllWords();
            List<Keyword> wordList = keywordDB.getAllWords();

            String urlParameters = "";
            for(Keyword keyword:wordList){
                listBean list = new listBean();
                list.setUrl(Function.excuteGet("https://www.googleapis.com/customsearch/v1/?key=AIzaSyBb3wLexTbAqMdRB0JJw7ajcPE8555m-o4&cx=006483914068273192856:_qesssnwq6m&q="+keyword.getWord(), urlParameters));
                list.setKeyword(keyword.getWord());
                listbean.add(list);
            }
//            for(int i = 0; i<wordArray.length; i++){
//                listBean list = new listBean();
//                list.setUrl(Function.excuteGet("https://www.googleapis.com/customsearch/v1/?key=AIzaSyBb3wLexTbAqMdRB0JJw7ajcPE8555m-o4&cx=006483914068273192856:_qesssnwq6m&q="+wordArray[i].getWord(), urlParameters));
//                list.setKeyword(wordArray[i].getWord());
//                listbean.add(list);
//            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

                runOnUiThread(new Runnable() {
                    public void run() {
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String formatDate = sdfNow.format(date);
                        SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");


                        if((newsDB.getNewsCount()==0)||((newsDB.getNewsCount()!=0)&&(!((formatDate(newsDB.getNews().getTimestamp())).equals(fmtOut.format(date)))))){
                            Log.d("time : ",fmtOut.format(date));

                            if(((newsDB.getNewsCount()!=0)&&(!((formatDate(newsDB.getNews().getTimestamp())).equals(fmtOut.format(date)))))){
                                Log.d("dbTime : ", formatDate(newsDB.getNews().getTimestamp()));
                                List<News> newsList = newsDB.getAllNews();
                                for(News news : newsList){newsDB.deleteNews(news);}
                            }


                        if(listbean.size()!=0){ // Just checking if not empty
                            for(listBean listBean : listbean) {

                                try {

                                    JSONObject jsonObj = new JSONObject(listBean.getUrl());

                                    JSONArray arrayArticles = jsonObj.getJSONArray("items"); //뉴스 목록들 받아옴

                                    for (int i = 0, j = arrayArticles.length(); i < j; i++) {
                                        JSONObject obj = arrayArticles.getJSONObject(i); //obj는 뉴스 하나의 내용

                                        Log.d("News", obj.toString());

                                        News newsBean = new News();
                                        newsBean.setTitle(obj.getString("title"));
                                        Log.d("ENews", newsBean.getTitle());
                                        newsBean.setUrl(obj.getString("link"));
                                        newsBean.setDes(obj.getString("htmlSnippet"));
                                        JSONObject pageObj = obj.getJSONObject("pagemap");
                                        Log.d("ENews", pageObj.toString());
                                        JSONArray arrayMet = pageObj.getJSONArray("metatags");
                                        Log.d("ENews", arrayMet.toString());
                                        JSONObject objMet = arrayMet.getJSONObject(0);
                                        Log.d("ENews", objMet.toString());
                                        newsBean.setImg(objMet.getString("og:image"));
                                        Log.d("ENews", newsBean.getImg());
                                        //newsBean.setDes(objMet.getString("og:description"));
                                        //Log.d("ENews", newsBean.getDes());
                                        newsBean.setKey(listBean.getKeyword());
                                        Log.d("ENews", newsBean.getKey());
                                        long id = newsDB.insertNews(newsBean);
                                        if(id>=0) Log.d("InsertSuccess", Long.toString(id));
                                        if(id<0) Log.d("InsertFailed", Long.toString(id));
                                    }

                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
                        }
                        }

                        // Start your app main activity
                        Intent i = new Intent(Splash.this, MainActivity.class);
                        startActivity(i);

                        // close this activity
                        finish();

                    }
                });
            }
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