package com.example.kit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kit.Adapter.ScrapNewsKAdapter;
import com.example.kit.Bean.ScrapNewsKBean;
import com.example.kit.DB.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/*
 * by Seonmi Hyeon
 *
 * */

public class scrapFragment extends Fragment {

    private List<ScrapNewsKBean> mScrapNewsList;

    public RecyclerView mRecyclerview;
    private ScrapNewsKAdapter adapter;

    /* db */
    private DatabaseHelper db;

    public scrapFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scrap,container,false);

        mRecyclerview = (RecyclerView) view.findViewById(R.id.scrapList);
        mRecyclerview.setHasFixedSize(false);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(view.getContext()));

        db = new DatabaseHelper(view.getContext());


        /* 스크랩시 데이터 받아오기 */
        /* 국내 해외 뉴스에서 처리 */

        /* DB에 저장된 키워드를 LIST에 추가 */
        mScrapNewsList = new ArrayList<>();

        List<String> keywords = db.getKeyword();
        for(int i = 0; i <keywords.size() ; i ++) {
            ScrapNewsKBean scrapNewsk = new ScrapNewsKBean(keywords.get(i));
            mScrapNewsList.add(scrapNewsk);
        };

        adapter =  new ScrapNewsKAdapter(mScrapNewsList,view.getContext());
        mRecyclerview.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    // TODO: Rename and change types and number of parameters
    public static scrapFragment newInstance() {
        scrapFragment fragment = new scrapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

}
