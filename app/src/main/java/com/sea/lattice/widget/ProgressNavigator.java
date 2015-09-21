package com.sea.lattice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sea.lattice.R;

/**
 * Created by Sea on 9/20/2015.
 */
public class ProgressNavigator extends LinearLayout {
    private LinearLayout titleBar;

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

    private void init(){
        inflate(getContext(), R.layout.progressnavigator, this);
        titleBar = (LinearLayout)this.findViewById(R.id.titlebar);
    }

    public void forword(String title){
        inflate(getContext(), R.layout.progressnavigator_bar, titleBar);
        Log.v("pn", "forward");
        TextView textView = (TextView)titleBar.getChildAt(titleBar.getChildCount()-1);
        textView.setText(title);
        textView.setId(titleBar.getChildCount());
        textView.setOnClickListener(new OnBarClickListener());
    }

    private void pop(int id){
        int i = titleBar.getChildCount()-1;
        while (titleBar.getChildAt(i).getId()!=id){
            titleBar.removeViewAt(i);
            i--;
        }
    }

    class OnBarClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            pop(v.getId());
        }
    }

}
