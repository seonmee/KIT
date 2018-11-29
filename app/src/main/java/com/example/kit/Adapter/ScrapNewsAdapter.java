package com.example.kit.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kit.Bean.ScrapNewsBean;
import com.example.kit.CustomDialog;
import com.example.kit.DB.DatabaseHelper;
import com.example.kit.MainActivity;
import com.example.kit.NewsDetail;
import com.example.kit.R;

import java.util.List;

/*
 * by Seonmi Hyeon
 *
 * */

public class ScrapNewsAdapter extends RecyclerView.Adapter<ScrapNewsAdapter.ViewHolder> {

    public List<ScrapNewsBean> mNewsList;
    private Context mContext;
    private DatabaseHelper mDb;


    public ScrapNewsAdapter(List<ScrapNewsBean> newsList,Context context, DatabaseHelper db){
        mNewsList = newsList;
        mContext = context;
        mDb = db;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView memo;
        ImageView remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title_scrap);
            memo = (ImageView)itemView.findViewById(R.id.memo);
            remove = (ImageView)itemView.findViewById(R.id.remove);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.scrap_cell,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        final ScrapNewsBean scrapNews = mNewsList.get(position);

        viewHolder.title.setText(scrapNews.getmTitle());
        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 뉴스 링크로 이동 */
                Intent intent = new Intent(mContext,NewsDetail.class);
                intent.putExtra("content", mDb.getUrl(viewHolder.title.getText().toString()) );
                mContext.startActivity(intent);
            }
        });

        viewHolder.memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 키워드에 해당하는 news list 의  position 번쩨 메모 */
                CustomDialog dialog = new CustomDialog(mContext,scrapNews);
                dialog.show();
            }
        });

        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("정말 메모를 삭제 하시겠습니까?");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* list 에서 제거  */
                        mNewsList.remove(viewHolder.getAdapterPosition());
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyItemRangeChanged(viewHolder.getAdapterPosition(),mNewsList.size());
                        /* db 에서 제거 */
                        mDb.delete(viewHolder.title.getText().toString());
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


}
