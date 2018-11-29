package com.example.kit;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.kit.database.KeywordDatabaseHelper;
import com.example.kit.database.NewsDatabaseHelper;
import com.example.kit.database.model.Keyword;
import com.example.kit.database.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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


    public newsKoreanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.fab:
                anim();
                Toast.makeText(getActivity(), "Floating Action Button", Toast.LENGTH_SHORT).show();
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

        getNews();

//        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),keywordDialog.class);
//                intent.putExtra("page", 0);
//                startActivityForResult(intent,10001);
//            }
//
//        });

        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) view.findViewById(R.id.fab2);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);


        //1. 화면이 로딩되면 뉴스정보를 받아온다.
        //2. 받아온 정보를 Adapter에 넘겨준다.
        //3. Adapter를 셋팅한다.

        return view;

    }

    public void getNews() {

        db = new KeywordDatabaseHelper(getActivity());
        newsDB = new NewsDatabaseHelper(getActivity());
        List<Keyword> keywords = db.getAllKeywords();
        if(keywords.size()==0){

            keywords = db.getAllWords();
        }
        List<News> newsList = new ArrayList<>();
        List<News> getNewsList = new ArrayList<>();
        for(Keyword keyword : keywords){
            getNewsList = newsDB.getNews(keyword.getWord());
            for(News news : getNewsList){
                newsList.add(news);
            }
            getNewsList.clear();
        }
        List<newsBean> newsBeans = new ArrayList<>();
        for(News news : newsList){
            newsBean newsBean = new newsBean();
            newsBean.setUrl(news.getUrl());
            newsBean.setContent(news.getDes());
            newsBean.setTitle(news.getTitle());
            newsBean.setKeyword(news.getKey());
            newsBean.setUrlToImage(news.getImg());
            newsBeans.add(newsBean);
        }

        mAdapter = new NewsAdapter(newsBeans, getActivity(), new View.OnClickListener() {
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
        mRecyclerView.setAdapter(mAdapter);//정상적으로 처리


//        for(int i = 1; i<3; i++){
//            for(Keyword keyword : keywords){
//                listBean listBean = new listBean();
//                listBean.setKeyword(keyword.getWord());
//                listBean.setUrl("https://www.googleapis.com/customsearch/v1?key=AIzaSyBCkBYSKgRZrNveVLYcHouy-764y0l-XxY&cx=000650060222557471131:igl-qjs8vfc&q="+keyword.getWord()+"&start="+((10*i)+1));
//                urlList.add(listBean);
//            }
//        }

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
            getNews();
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



}

/*

    public void getNews() {
        db = new KeywordDatabaseHelper(getActivity());
        List<Keyword> keywords = db.getAllKeywords();
        if(keywords.size()==0){

            Keyword wordArray [] = db.getAllWords();
            for(int i = 0; i<wordArray.length; i++){
                keywords.add(wordArray[i]);
            }
        }
        // Instantiate the RequestQueue.
        List<listBean> urlList = new ArrayList<>();
        for(Keyword keyword : keywords){
            listBean listBean = new listBean();
            listBean.setKeyword(keyword.getWord());
            listBean.setUrl("https://www.googleapis.com/customsearch/v1/siterestrict?key=AIzaSyBCkBYSKgRZrNveVLYcHouy-764y0l-XxY&cx=000650060222557471131:igl-qjs8vfc&q="+keyword.getWord());
            urlList.add(listBean);
        }
//        for(int i = 1; i<3; i++){
//            for(Keyword keyword : keywords){
//                listBean listBean = new listBean();
//                listBean.setKeyword(keyword.getWord());
//                listBean.setUrl("https://www.googleapis.com/customsearch/v1?key=AIzaSyBCkBYSKgRZrNveVLYcHouy-764y0l-XxY&cx=000650060222557471131:igl-qjs8vfc&q="+keyword.getWord()+"&start="+((10*i)+1));
//                urlList.add(listBean);
//            }
//        }
        final List<newsBean> news = new ArrayList<>();

// Request a string response from the provided URL.

 */