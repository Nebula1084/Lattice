package com.sea.lattice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.sea.lattice.R;

/**
 * Created by Sea on 9/20/2015.
 */
public class ProgressNavigator extends LinearLayout {
    private LinearLayout titleBar;

    public abstract class OnBackwardListener {
        View view;

        public OnBackwardListener(View view) {
            this.view = view;
        }

        abstract public void OnBackward(View view);
    }

    public ProgressNavigator(Context context) {
        super(context);
        init();
    }

    public ProgressNavigator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressNavigator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.progressnavigator, this);
        titleBar = (LinearLayout) this.findViewById(R.id.titlebar);
    }

    public void forword(String title, OnBarClickListener onBarClickListener) {
        inflate(getContext(), R.layout.progressnavigator_bar, titleBar);
        TextView textView = (TextView) titleBar.getChildAt(titleBar.getChildCount() - 1);
        textView.setText(title);
        textView.setId(titleBar.getChildCount());
        textView.setOnClickListener(onBarClickListener);
    }

    public void forword(String title) {
        forword(title, new OnBarClickListener(this));
    }

    private void pop(int id) {
        int i = titleBar.getChildCount() - 1;
        while (titleBar.getChildAt(i).getId() != id) {
            titleBar.removeViewAt(i);
            i--;
        }
    }

    public void pop() {
        int i = titleBar.getChildCount() - 1;
        titleBar.removeViewAt(i);
    }

    public static class OnBarClickListener implements OnClickListener {
        ProgressNavigator progressNavigator;

        public OnBarClickListener(ProgressNavigator progressNavigator) {
            this.progressNavigator = progressNavigator;
        }

        @Override
        public void onClick(View v) {
            progressNavigator.pop(v.getId());
        }
    }

}
