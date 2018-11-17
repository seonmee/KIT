package com.example.kit.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kit.Bean.ScrapNewsBean;
import com.example.kit.R;

import java.util.List;

/*
 * by Seonmi Hyeon
 *
 * */

public class ScrapNewsAdapter extends RecyclerView.Adapter<ScrapNewsAdapter.ViewHolder> {

    private List<ScrapNewsBean> mNewsList;

    public ScrapNewsAdapter(List<ScrapNewsBean> newsList){
        mNewsList = newsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView memo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title_scrap);
            memo = (ImageView)itemView.findViewById(R.id.memo);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scrap_cell,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        ScrapNewsBean scrapNews = mNewsList.get(position);

        viewHolder.title.setText(scrapNews.getmTitle());
        viewHolder.memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 다이얼로그 */
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
