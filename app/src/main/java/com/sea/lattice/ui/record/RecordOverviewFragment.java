package com.sea.lattice.ui.record;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sea.lattice.R;
import com.sea.lattice.ui.DefaultFragmentPagerAdapter;
import com.sea.lattice.ui.Navigator;

/**
 * Created by Sea on 9/26/2015.
 */
public class RecordOverviewFragment extends Fragment{
    private Navigator navigator;
    private DefaultFragmentPagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_record, container, false);

        navigator = (Navigator) rootView.findViewById(R.id.screen_record_pager);
        mPagerAdapter = new DefaultFragmentPagerAdapter(getChildFragmentManager());
        mPagerAdapter.addFragment(getString(R.string.str_record), new RecordFragment());
        mPagerAdapter.addFragment(getString(R.string.record_choose_template), new TemplateFragment());
        navigator.setAdapter(mPagerAdapter);
        navigator.setFocusableInTouchMode(false);

        return rootView;
    }
}
