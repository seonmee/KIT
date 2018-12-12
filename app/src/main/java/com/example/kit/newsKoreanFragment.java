package com.example.kit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kit.Adapter.NewsAdapter;
import com.example.kit.Bean.listBean;
import com.example.kit.Bean.newsBean;
import com.example.kit.Adapter.NewsAdapter;
import com.example.kit.Bean.newsBean;
import com.example.kit.Model.FilterItemModel;
import com.example.kit.database.ENewsDatabaseHelper;
import com.example.kit.database.KeywordDatabaseHelper;
import com.example.kit.database.NewsDatabaseHelper;
import com.example.kit.database.model.Keyword;
import com.example.kit.database.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class newsKoreanFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"1","2"};
    private int newsNum;
    RequestQueue queue;
    private KeywordDatabaseHelper db;
    private NewsDatabaseHelper newsDB;

    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;

    private List<newsBean> mNewsBeans;

    public newsKoreanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                Intent intent = new Intent(getActivity(),keywordDialog.class);
                intent.putExtra("page", 0);
                startActivityForResult(intent,10001);
                break;
            case R.id.fab2:
                anim();
                Toast.makeText(getActivity(), "첫화면 뉴스", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_korean,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //queue = Volley.newRequestQueue(view.getContext()); //초기화

        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        if(Function.isNetworkAvailable(view.getContext()))
        {
            new Thread() {
                public void run() {
                    prepareNews();
                }
            }.start();
        }else{
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        //1. 화면이 로딩되면 뉴스정보를 받아온다.
        //2. 받아온 정보를 Adapter에 넘겨준다.
        //3. Adapter를 셋팅한다.

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread() {
            public void run() {
                prepareNews();
            }
        }.start();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.commit();
    }

    public static newsKoreanFragment newInstance() {
        newsKoreanFragment fragment = new newsKoreanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == Activity.RESULT_OK)) {
            new Thread() {
                public void run() {
                    prepareNews();
                }
            }.start();
        }
        // recreate your fragment here
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.commit();
    }

    public void anim() {

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    public void prepareNews(){
        db = new KeywordDatabaseHelper(getActivity());
        newsDB = new NewsDatabaseHelper(getActivity());
        List<Keyword> wordList = new ArrayList<>();
        if(db.getKeywordsCount()>0) {
            wordList = db.getAllKeywords();
        }
        List<listBean> listbean = new ArrayList<>();
        List<News> newsList = new ArrayList<>();

        List<News> getNewsList = new ArrayList<>(); //임시
        mNewsBeans = new ArrayList<>();
        String urlParameters = "";
        if(wordList.size()>0){
            for(Keyword word : wordList){
                if(newsDB.hasNews(word.getWord())>0) {
                    getNewsList = newsDB.getNews(word.getWord());
                    for(News news : getNewsList){
                        newsList.add(news);
                    }
                    getNewsList.clear();
                }
                else {
                    listBean list = new listBean();
                    list.setUrl("https://newsapi.org/v2/everything?q=" + word.getWord() + "&apiKey=84c04b988ee542a38f94ae96abc50406");
                    list.setKeyword(word.getWord());
                    listbean.add(list);
                }
            }
        }
        else{
            listBean list = new listBean();
            list.setUrl("https://newsapi.org/v2/top-headlines?country=kr&category=technology&apiKey=84c04b988ee542a38f94ae96abc50406");
            list.setKeyword("IT 기타");
            listbean.add(list);
        }

        String str;
        String receiveMsg = null;
        if(listbean.size()!=0){ // Just checking if not empty
            for(listBean listBean : listbean) {

                URL url = null;
                try {
                    url = new URL(listBean.getUrl());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                    if (conn.getResponseCode() == conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        Log.i("receiveMsg : ", receiveMsg);

                        reader.close();
                    } else {
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {

                    JSONObject jsonObj = new JSONObject(receiveMsg);

                    JSONArray arrayArticles = jsonObj.getJSONArray("articles"); //뉴스 목록들 받아옴

                    for (int i = 0, j = arrayArticles.length(); i < j; i++) {
                        JSONObject obj = arrayArticles.getJSONObject(i); //obj는 뉴스 하나의 내용

                        Log.d("News", obj.toString());

                        News newsBean = new News();
                        newsBean.setTitle(obj.getString("title"));
                        newsBean.setImg(obj.getString("urlToImage"));
                        newsBean.setDes(obj.getString("description"));
                        newsBean.setUrl(obj.getString("url"));
                        newsBean.setKey(listBean.getKeyword());
                        newsList.add(newsBean);
                    }

                } catch (JSONException e) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast.makeText(getActivity(), "Unexpected error", Toast.LENGTH_SHORT).show();                        }
//                    });

                }
            }

        }else{
//            getActivity().runOnUiThread(new Runnable() {
//                public void run() {
//                    Toast.makeText(getActivity(), "No news found", Toast.LENGTH_SHORT).show();                        }
//            });
        }

        for(News news : newsList){
            newsBean newsBean = new newsBean();
            newsBean.setUrl(news.getUrl());
            newsBean.setContent(news.getDes());
            newsBean.setTitle(news.getTitle());
            newsBean.setKeyword(news.getKey());
            newsBean.setUrlToImage(news.getImg());
            mNewsBeans.add(newsBean);
        }

        mAdapter = new NewsAdapter(mNewsBeans, getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object obj = v.getTag();
                if(obj != null){
                    int position = (int) obj;
                    Intent intent = new Intent(getActivity(),NewsDetail.class);
                    intent.putExtra("content", ((NewsAdapter)mAdapter).getNews(position).getUrl());
                    startActivity(intent);
                }
                // Instantiate the RequestQueue.


            }

        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        //mRecyclerView.setAdapter(mAdapter);//정상적으로 처리

        if(wordList.size()>0){
            for(Keyword word : wordList){
                if(newsDB.hasNews(word.getWord())<1) {
                    for(News news : newsList){
                        if(news.getKey().equals(word.getWord())){
                            long id = newsDB.insertNews(news);
                        }
                    }
                }
            }
        }
    }
}