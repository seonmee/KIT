package com.example.kit;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.kit.Adapter.CustomFilterAdapter;
import com.example.kit.Event.FilterCallback;
import com.example.kit.Model.FilterItemModel;
import com.example.kit.Utils.FilterUtils;
import com.example.kit.database.KeywordDatabaseHelper;
import com.example.kit.database.model.Keyword;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class keywordDialog extends Activity implements FilterCallback {
    @BindView(R.id.section_filter_recycler)
    RecyclerView filterRecyclerView;
    @BindView(R.id.section_filter)
    TextView sectionFilter;
    @BindView(R.id.section_separator)
    View sectionSeparator;
    @BindView(R.id.category_filter_recycler)
    RecyclerView categoryRecyclerView;
    @BindView(R.id.category_filter)
    TextView categoryFilter;
    @BindView(R.id.category_separator)
    View categorySeparator;

    private CustomFilterAdapter customCategoryAdapter;
    private List<FilterItemModel> categories;
    private CustomFilterAdapter customFilterAdapter;
    private boolean isCategoryFilterExpanded = false;
    private KeywordDatabaseHelper db;
    private int page;
    android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.keyword_dialog);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int x = (int)(size.x*0.9f);
        int y = (int)(size.y*0.7f);
        params.width = x;
        if(params.height > y) {
            this.getWindow().setLayout(x,y);
        }
        //최대 height 지정해보려고 했는데 안돼요ㅠㅠ
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ButterKnife.bind(this);

        int colors [] = getResources().getIntArray(R.array.colors);
        String sections [] = getResources().getStringArray(R.array.titles);
        db = new KeywordDatabaseHelper(this);
        //Keyword keywords [] = db.getAllWords();
        List<Keyword> keywords = db.getAllWords();

        List<FilterItemModel> filters = Arrays.asList(
                new FilterItemModel(1, sections[0], colors[0], 0, 0),
                new FilterItemModel(2, sections[1], colors[1], 0, 0),
                new FilterItemModel(3, sections[2], colors[2],0, 0),
                new FilterItemModel(4, sections[3], colors[3], 0, 0),
                new FilterItemModel(5, sections[4], colors[4], 0, 0),
                new FilterItemModel(6, sections[5], colors[0], 0, 0)
        );

        categories = Arrays.asList(
                new FilterItemModel(0,  keywords.get(0).getWord(), colors[0], keywords.get(0).getIsSelected(), 1),
                new FilterItemModel(0,  keywords.get(1).getWord(), colors[0], keywords.get(1).getIsSelected(), 1),
                new FilterItemModel(0,  keywords.get(2).getWord(), colors[0], keywords.get(2).getIsSelected(), 1),
                new FilterItemModel(0,  keywords.get(3).getWord(), colors[0], keywords.get(3).getIsSelected(), 1),
                new FilterItemModel(0,  keywords.get(4).getWord(), colors[0], keywords.get(4).getIsSelected(), 1),
                new FilterItemModel(0,  keywords.get(5).getWord(), colors[0], keywords.get(5).getIsSelected(), 1),
                new FilterItemModel(0,  keywords.get(6).getWord(), colors[0], keywords.get(6).getIsSelected(), 1),
                new FilterItemModel(0,  keywords.get(7).getWord(), colors[0], keywords.get(7).getIsSelected(), 1)
//                new FilterItemModel(0,  keywords[8].getWord(), colors[0], keywords[8].getIsSelected(), 2),
//                new FilterItemModel(0,  keywords[9].getWord(), colors[0], keywords[9].getIsSelected(), 2),
//                new FilterItemModel(0,  keywords[10].getWord(), colors[0], keywords[10].getIsSelected(), 2),
//                new FilterItemModel(0,  keywords[11].getWord(), colors[0], keywords[11].getIsSelected(), 2),
//                new FilterItemModel(0,  keywords[12].getWord(), colors[0], keywords[12].getIsSelected(), 3),
//                new FilterItemModel(0,  keywords[13].getWord(), colors[0], keywords[13].getIsSelected(), 3),
//                new FilterItemModel(0,  keywords[14].getWord(), colors[0], keywords[14].getIsSelected(), 3),
//                new FilterItemModel(0,  keywords[15].getWord(), colors[0], keywords[15].getIsSelected(), 4),
//                new FilterItemModel(0,  keywords[16].getWord(), colors[0], keywords[16].getIsSelected(), 4),
//                new FilterItemModel(0,  keywords[17].getWord(), colors[0], keywords[17].getIsSelected(), 4),
//                new FilterItemModel(0,  keywords[18].getWord(), colors[0], keywords[18].getIsSelected(), 4),
//                new FilterItemModel(0,  keywords[19].getWord(), colors[0], keywords[19].getIsSelected(), 4),
//                new FilterItemModel(0,  keywords[20].getWord(), colors[0], keywords[20].getIsSelected(), 4),
//                new FilterItemModel(0,  keywords[21].getWord(), colors[0], keywords[21].getIsSelected(), 5),
//                new FilterItemModel(0,  keywords[22].getWord(), colors[0], keywords[22].getIsSelected(), 5),
//                new FilterItemModel(0,  keywords[23].getWord(), colors[0], keywords[23].getIsSelected(), 6),
//                new FilterItemModel(0,  keywords[24].getWord(), colors[0], keywords[24].getIsSelected(), 6),
//                new FilterItemModel(0,  keywords[25].getWord(), colors[0], keywords[25].getIsSelected(), 6),
//                new FilterItemModel(0,  keywords[26].getWord(), colors[0], keywords[26].getIsSelected(), 6),
//                new FilterItemModel(0,  keywords[27].getWord(), colors[0], keywords[27].getIsSelected(), 6),
//                new FilterItemModel(0,  keywords[28].getWord(), colors[0], keywords[28].getIsSelected(), 6)
        );

        customFilterAdapter = new CustomFilterAdapter(this, filters, this);
        customCategoryAdapter = new CustomFilterAdapter(this, categories, this);

        filterRecyclerView.setLayoutManager(new FlexboxLayoutManager(this));
        filterRecyclerView.setAdapter(customFilterAdapter);
        categoryRecyclerView.setLayoutManager(new FlexboxLayoutManager(this));
        categoryRecyclerView.setAdapter(customCategoryAdapter);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        page = bundle.getInt("page");

    }

    @OnClick(R.id.section_filter)
    public void toggleSectionFilter(){
        if (filterRecyclerView.getVisibility() == View.GONE){
            FilterUtils.expand(filterRecyclerView);
            sectionSeparator.setVisibility(View.VISIBLE);
            sectionFilter.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.arrow_up, 0);
        } else {
            FilterUtils.collapse(filterRecyclerView);
            sectionSeparator.setVisibility(View.GONE);
            sectionFilter.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.arrow_down,0);
        }
    }

    @OnClick(R.id.category_filter)
    public void toggleCategoryFilter(){
        loadSubCategories();
        if (categoryRecyclerView.getVisibility() == View.GONE){
            FilterUtils.expand(categoryRecyclerView);
            categorySeparator.setVisibility(View.VISIBLE);
            categoryFilter.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.arrow_up, 0);
            isCategoryFilterExpanded = true;
        } else {
            FilterUtils.collapse(categoryRecyclerView);
            categorySeparator.setVisibility(View.GONE);
            categoryFilter.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.arrow_down,0);
            isCategoryFilterExpanded = false;
        }
    }


    @Override
    public void onFilterSelect(FilterItemModel filter) {
        if (filter.getParent_id() == 0){
            if (!isCategoryFilterExpanded){
                categoryFilter.performClick();
            } else {
                loadSubCategories();
            }
        }
    }

    @Override
    public void onFilterDeselect(FilterItemModel filter) {
        if (filter.getParent_id() == 0){
            loadSubCategories();
        }
    }

    private void loadSubCategories(){
        List<FilterItemModel> tempCategories = new ArrayList<>();
        for (FilterItemModel category : categories) {
            for (FilterItemModel filterItemModel : customFilterAdapter.getSelectedFilters()) {
                if (category.getParent_id() == filterItemModel.getId()){
                    tempCategories.add(category);
                }
            }
            customCategoryAdapter.addFilters(tempCategories);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
    public void buttonSave(View v){
        for(FilterItemModel model : categories){
            if(model.isSelected()==1) db.selectKeyword(model.getTitle());
            else db.unselectKeyword(model.getTitle());
        }
//        if(page==0){
//            Intent intent = new Intent(this, newsKoreanFragment.class);
//            startActivity(intent);
//        }
//        else if(page==1){
//            Intent intent = new Intent(this, newsEnglishFragment.class);
//            startActivity(intent);
//        }
        setResult(Activity.RESULT_OK);
        finish();
    }
    public void buttonCancel(View v){

        finish();
    }

}
