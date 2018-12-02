package com.example.kit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kit.Bean.ScrapNewsBean;
import com.example.kit.Bean.ScrapNewsKBean;
import com.example.kit.DB.DatabaseHelper;
import com.example.kit.R;

import java.util.List;

/*
 * by Seonmi Hyeon
 *
 * */

public class ScrapNewsKAdapter extends RecyclerView.Adapter<ScrapNewsKAdapter.ViewHolder> {

    private List<ScrapNewsKBean> mNewsKList;
    private Context mContext;

    private ScrapNewsAdapter mScrapNewsAdapter;
    private List<ScrapNewsBean> mScrapNewsList;
    private DatabaseHelper mDb;

    public ScrapNewsKAdapter(List<ScrapNewsKBean> scrapNewsK, Context context){
        mNewsKList = scrapNewsK;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView keyword;
        RecyclerView newskList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keyword = (TextView) itemView.findViewById(R.id.keyword);

            newskList = (RecyclerView) itemView.findViewById(R.id.newslist_k);
            newskList.setHasFixedSize(false);
            newskList.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scrap_kcell,viewGroup,false);
        mDb = new DatabaseHelper(view.getContext());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        ScrapNewsKBean scrapNewsK = mNewsKList.get(position);

        viewHolder.keyword.setText("# "+ scrapNewsK.getmKeyword());

        /* DB에서 키워드에 뉴스LIST를 불러와 List에 저장  */
        mScrapNewsList = mDb.getNewsItem(scrapNewsK.getmKeyword());
        mScrapNewsAdapter = new ScrapNewsAdapter(mScrapNewsList,mContext);
        viewHolder.newskList.setAdapter(mScrapNewsAdapter);

    }

    @Override
    public int getItemCount() {
        return mNewsKList.size();
    }

}
