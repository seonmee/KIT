package com.example.kit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.kit.Event.FilterCallback;
import com.example.kit.Model.FilterItemModel;
import com.example.kit.MyBounceInterpolator;
import com.example.kit.R;
import com.example.kit.database.KeywordDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomFilterAdapter extends RecyclerView.Adapter<CustomFilterAdapter.ViewHolder>{

    private Context context;
    private List<FilterItemModel> filters;
    private FilterCallback filterCallback;
    private KeywordDatabaseHelper db;

    public CustomFilterAdapter(Context context, List<FilterItemModel> filters, FilterCallback filterCallback) {
        this.context = context;
        this.filters = filters;
        this.filterCallback = filterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            FilterItemModel filter = filters.get(position);
            holder.filter.setText(filter.getTitle());
            setSelected(holder.filter, filter);
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.section_filter_recycler)
        TextView filter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            filter.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.05, 30);
            myAnim.setInterpolator(interpolator);
            v.startAnimation(myAnim);
            FilterItemModel filter = filters.get(getAdapterPosition());
            if(filter.isSelected()==0){
                filter.setSelected(1);
            } else filter.setSelected(0);
            //filter.setSelected(!filter.isSelected());
            setSelected((TextView) v, filter);
            if (filter.isSelected()==1){
                filterCallback.onFilterSelect(filter);
            } else {
                filterCallback.onFilterDeselect(filter);
            }
        }
    }

    public void addFilters(List<FilterItemModel> filters){
        this.filters = filters;
        notifyDataSetChanged();
    }

    public List<FilterItemModel> getSelectedFilters(){
        List<FilterItemModel> tempFilters = new ArrayList<>();
        for (FilterItemModel filter : filters) {
            if (filter.isSelected()==1){
                tempFilters.add(filter);
            }
        }
        return tempFilters;
    }

    public void delesectAll(){
        for (FilterItemModel filter : filters) {
            filter.setSelected(0);
        }
    }

    private void setSelected(TextView filterItem, FilterItemModel filter){
        GradientDrawable drawable = (GradientDrawable) filterItem.getBackground();
        if (filter.isSelected()==1){
            drawable.setColor(filter.getColor());
            drawable.setStroke(2, filter.getColor());
            filterItem.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            drawable.setStroke(2, Color.parseColor("#404671"));
            drawable.setColor(0xFFFFFF);
            filterItem.setTextColor(Color.parseColor("#000000"));
        }
    }
}


