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
import android.widget.AdapterView;

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

public class newsKoreanFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] mDataset = {"1","2"};
    private int newsNum;
    RequestQueue queue;
    private KeywordDatabaseHelper db;
    private NewsDatabaseHelper newsDB;


    public newsKoreanFragment() {
        // Required empty public constructor
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

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),keywordDialog.class);
                intent.putExtra("page", 0);
                startActivityForResult(intent,10001);
            }

        });

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
                    //((NewsAdapter)mAdapter).getNews(position).getContent();
                    //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(((NewsAdapter)mAdapter).getNews(position).getUrl()));
                    //startActivity(intent);
                    Intent intent = new Intent(getActivity(),NewsDetail.class);
                    intent.putExtra("content", ((NewsAdapter)mAdapter).getNews(position).getUrl());
                    startActivity(intent);
                }

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

        List<StringRequest> SR = new ArrayList<>();
        newsNum = 10;
        for(listBean ListBean : urlList){
            if(newsNum==10){
                StringRequest stringRequest = new StringRequest(Request.Method.GET, ListBean.getUrl(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //Log.d("News", response);

                                try {
                                    JSONObject jsonObj = new JSONObject(response);

                                    JSONArray arrayArticles = jsonObj.getJSONArray("items"); //뉴스 목록들 받아옴

                                    //response ->> NewsData Class 분류
                                    JSONObject objQueries = jsonObj.getJSONObject("queries");
                                    JSONArray arrayRequest = objQueries.getJSONArray("request");
                                    JSONObject objRequest = arrayRequest.getJSONObject(0);
                                    newsNum = objRequest.getInt("count");

                                    for (int i = 0, j = arrayArticles.length(); i < j; i++) {
                                        JSONObject obj = arrayArticles.getJSONObject(i); //obj는 뉴스 하나의 내용

                                        //Log.d("News", obj.toString());

                                        newsBean newsBean = new newsBean();
                                        newsBean.setTitle(obj.getString("title"));
                                        //Log.d("ENews", newsBean.getTitle());
                                        newsBean.setUrl(obj.getString("link"));
                                        JSONObject pageObj = obj.getJSONObject("pagemap");
                                        //Log.d("ENews", pageObj.toString());
                                        JSONArray arrayMet = pageObj.getJSONArray("metatags");
                                        //Log.d("ENews", arrayMet.toString());
                                        JSONObject objMet = arrayMet.getJSONObject(0);
                                        //Log.d("ENews", objMet.toString());
                                        newsBean.setUrlToImage(objMet.getString("og:image"));
                                        //Log.d("ENews", newsBean.getUrlToImage());
                                        newsBean.setContent(objMet.getString("og:description"));
                                        news.add(newsBean);
                                    }

                                    // specify an adapter (see also next example)
                                    mAdapter = new NewsAdapter(news, getActivity(), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Object obj = v.getTag();
                                            if(obj != null){
                                                int position = (int) obj;
                                                //((NewsAdapter)mAdapter).getNews(position).getContent();
                                                //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(((NewsAdapter)mAdapter).getNews(position).getUrl()));
                                                //startActivity(intent);
                                                Intent intent = new Intent(getActivity(),NewsDetail.class);
                                                intent.putExtra("content", ((NewsAdapter)mAdapter).getNews(position).getUrl());
                                                startActivity(intent);
                                            }


                                        }

                                    });
                                    mRecyclerView.setAdapter(mAdapter);//정상적으로 처리


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                SR.add(stringRequest);
            }
            else break;
        }

// Add the request to the RequestQueue.
        for(StringRequest i : SR){
            queue.add(i);
        }
        //queue.add(stringRequest);
        //queue.add(stringRequest2);
    }

 */