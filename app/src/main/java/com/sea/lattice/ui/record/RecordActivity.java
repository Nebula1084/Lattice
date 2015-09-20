package com.sea.lattice.ui.record;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.sea.lattice.R;
import com.sea.lattice.ui.DefaultFragmentPagerAdapter;
import com.sea.lattice.ui.Navigator;


/**
 * Created by Sea on 9/15/2015.
 */
public class RecordActivity extends FragmentActivity implements View.OnClickListener{

    private Navigator navigator;
    private DefaultFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_record);
        navigator = (Navigator)this.findViewById(R.id.screen_record_pager);
        mPagerAdapter = new DefaultFragmentPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(getString(R.string.str_record),new RecordFragment());
        mPagerAdapter.addFragment(getString(R.string.record_choose_template),new TemplateFragment());
        navigator.setAdapter(mPagerAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
