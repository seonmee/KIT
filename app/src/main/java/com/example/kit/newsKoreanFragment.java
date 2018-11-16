package com.example.kit;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kit.Adapter.NewsAdapter;
import com.example.kit.Bean.newsBean;
import com.example.kit.Adapter.NewsAdapter;
import com.example.kit.Bean.newsBean;

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
    RequestQueue queue;


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

        queue = Volley.newRequestQueue(view.getContext()); //초기화
        getNews();

        //1. 화면이 로딩되면 뉴스정보를 받아온다.
        //2. 받아온 정보를 Adapter에 넘겨준다.
        //3. Adapter를 셋팅한다.

        return view;

    }

    public void getNews() {
        // Instantiate the RequestQueue.
        String url ="https://www.googleapis.com/customsearch/v1?key=AIzaSyBCkBYSKgRZrNveVLYcHouy-764y0l-XxY&cx=000650060222557471131:igl-qjs8vfc&q=블록체인";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //Log.d("News", response);

                        try {

                            JSONObject jsonObj = new JSONObject(response);

                            JSONArray arrayArticles = jsonObj.getJSONArray("items"); //뉴스 목록들 받아옴

                            //response ->> NewsData Class 분류
                            List<newsBean> news = new ArrayList<>();

                            for (int i = 0, j = arrayArticles.length(); i < j; i++) {
                                JSONObject obj = arrayArticles.getJSONObject(i); //obj는 뉴스 하나의 내용

                                Log.d("News", obj.toString());

                                newsBean newsBean = new newsBean();
                                newsBean.setTitle(obj.getString("title"));
                                Log.d("ENews", newsBean.getTitle());
                                newsBean.setUrl(obj.getString("link"));
                                JSONObject pageObj = obj.getJSONObject("pagemap");
                                Log.d("ENews", pageObj.toString());
                                JSONArray arrayMet = pageObj.getJSONArray("metatags");
                                Log.d("ENews", arrayMet.toString());
                                JSONObject objMet = arrayMet.getJSONObject(0);
                                Log.d("ENews", objMet.toString());
                                newsBean.setUrlToImage(objMet.getString("og:image"));
                                Log.d("ENews", newsBean.getUrlToImage());
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

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static newsKoreanFragment newInstance() {
        newsKoreanFragment fragment = new newsKoreanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}