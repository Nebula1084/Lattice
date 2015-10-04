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

/**
 * Created by Sea on 9/19/2015.
 */
public class Navigator extends LinearLayout {
    private LinearLayout navigator_bar;
    private ImageView navigator_cursor;
    private ViewPager pager;
    private FragmentPagerAdapter mFragmentPagerAdapter;
    private int currentItem = 0;
    private int itemWidth;

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
        navigator_bar = (LinearLayout) this.findViewById(R.id.navigator_bar);
        navigator_cursor = (ImageView) this.findViewById(R.id.navigator_cursor);
        pager = (ViewPager) this.findViewById(R.id.pager);
    }

    public void setAdapter(FragmentPagerAdapter fragmentPagerAdapter) {
        mFragmentPagerAdapter = fragmentPagerAdapter;
        pager.setAdapter(mFragmentPagerAdapter);
        pager.addOnPageChangeListener(new PagerScrollListener());
        navigator_bar.removeAllViews();
        for (int i = 0; i < mFragmentPagerAdapter.getCount(); i++) {

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(getContext().LAYOUT_INFLATER_SERVICE);

            inflater.inflate(R.layout.navigator_bar, navigator_bar);
            TextView textView = (TextView) navigator_bar.getChildAt(i);
            textView.setText(mFragmentPagerAdapter.getPageTitle(i));
            textView.setOnClickListener(new OnBarClickListener(i));
        }
        ViewGroup.LayoutParams params = navigator_cursor.getLayoutParams();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        itemWidth = dm.widthPixels / mFragmentPagerAdapter.getCount();
        params.width = itemWidth;
        navigator_cursor.setLayoutParams(params);
        selectBar(0);
    }

    private class OnBarClickListener implements OnClickListener {
        private int position;

        OnBarClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            selectBar(position);
        }
    }

    private class PagerScrollListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectBar(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public void selectBar(int newPosition) {
        TextView oldItem = (TextView) navigator_bar.getChildAt(currentItem);
        oldItem.setTextColor(Color.BLACK);

        TextView currentView = (TextView) navigator_bar.getChildAt(newPosition);
        currentView.setTextColor(Color.BLUE);
        pager.setCurrentItem(newPosition);
        Animation animation = new TranslateAnimation(currentItem * itemWidth, newPosition * itemWidth, 0, 0);
        currentItem = newPosition;
        animation.setDuration(100);
        animation.setFillAfter(true);
        navigator_cursor.startAnimation(animation);
    }

}