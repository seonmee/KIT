package com.example.kit.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kit.newsEnglishFragment;
import com.example.kit.newsKoreanFragment;
import com.example.kit.scrapFragment;

public class FragementAdapter extends FragmentPagerAdapter {

    private static int PAGE_NUMBER = 3;

    public FragementAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return  newsKoreanFragment.newInstance();
            case 1 :
                return  newsEnglishFragment.newInstance();
            case 2 :
                return scrapFragment.newInstance();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "국내뉴스";
            case 1 :
                return "해외뉴스";
            case 2 :
                return  "스크랩뉴스";
            default:
                return  null;
        }
    }
}
