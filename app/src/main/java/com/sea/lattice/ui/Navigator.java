package com.sea.lattice.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sea.lattice.R;

import io.karim.MaterialTabs;

/**
 * Created by Sea on 9/19/2015.
 */
public class Navigator extends LinearLayout {
    private ViewPager pager;
    private MaterialTabs tabs;
    private FragmentPagerAdapter mFragmentPagerAdapter;

    public Navigator(Context context) {
        super(context);
        init();
    }

    public Navigator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Navigator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.navigator, this);
        tabs = (MaterialTabs) findViewById(R.id.material_tabs);
        pager = (ViewPager) this.findViewById(R.id.pager);

    }

    public void setAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
        mFragmentPagerAdapter = fragmentPagerAdapter;
        pager.setAdapter(mFragmentPagerAdapter);
        tabs.setViewPager(pager);
    }

    public void selectBar(int newPosition) {
        pager.setCurrentItem(newPosition);
    }

}