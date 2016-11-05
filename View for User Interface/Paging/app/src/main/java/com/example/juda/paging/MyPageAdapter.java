package com.example.juda.paging;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Juda on 05/11/2016.
 */

public class MyPageAdapter extends PagerAdapter {
    @Override
    public int getCount() {//the number of pages which we are paging between them
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)container.getContext().getSystemService(Context
        .LAYOUT_INFLATER_SERVICE);
        int resId = 0;
        switch (position){
            case 0:
                resId = R.layout.page1;
                break;
            case 1:
                resId = R.layout.page2;
                break;
            case 2:
                resId = R.layout.page3;
                break;
        }

        View view = inflater.inflate(resId, null);
        ((ViewPager)container).addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
