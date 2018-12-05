package com.example.kit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kit.Bean.newsBean;
import com.example.kit.DB.DatabaseHelper;
import com.example.kit.R;
import com.example.kit.database.model.News;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<newsBean> mDataset;
    private List<Boolean> mCheckstate;
    private static View.OnClickListener onClickListener;

    private static DatabaseHelper mDb;
    //int i = 0;

    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_title;
        public TextView TextView_content;
        public SimpleDraweeView ImageView_title;

        //public ImageView bookmark;
        public CheckBox bookmark;

        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_title = v.findViewById(R.id.TextView_title);
            TextView_content = v.findViewById(R.id.TextView_content);
            ImageView_title = v.findViewById(R.id.ImageView_title);

            bookmark = v.findViewById(R.id.ImageView_bookmark);

            //bookmark.setChecked();
            mDb = new DatabaseHelper(v.getContext());

            rootView = v;

            v.setClickable(true);
            v.setEnabled(true);
            v.setOnClickListener(onClickListener);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NewsAdapter(List<newsBean> myDataset, Context context, View.OnClickListener onClick) {
        mDataset = myDataset;
        onClickListener = onClick;

        mContext = context;

        Fresco.initialize(context);
        //Activity contㅠㅐext 메모리 누수 발생 가능ㅠㅠ
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_cell, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) { //position에 있는 정보를 가져오기
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final newsBean news = mDataset.get(position);

        holder.TextView_title.setText(news.getTitle());
        String content = news.getContent();

        if (content != null && content.length() > 0) {
            holder.TextView_content.setText(Html.fromHtml(content));
        }
        Uri uri = Uri.parse(news.getUrlToImage());

        holder.ImageView_title.setImageURI(uri);
        //tag
        holder.rootView.setTag(position);

        if (mDb.getCheck(news.getTitle()) != null) {
            holder.bookmark.setChecked(mDb.getCheck(news.getTitle()));
        }
        else{
            holder.bookmark.setChecked(false);
        }

        /* 스크랩 */
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox)v).isChecked()){
                    mDb.createScrab(news.getKeyword(), news.getTitle(), news.getUrl());
                }
                else {
                    mDb.delete(news.getTitle());
                }

                /* 누르면 저장되고 */
                mDb.setCheck(((CheckBox)v).isChecked(),news.getTitle());
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public newsBean getNews(int position) {
        return mDataset != null ?  mDataset.get(position) : null;
    }
}
