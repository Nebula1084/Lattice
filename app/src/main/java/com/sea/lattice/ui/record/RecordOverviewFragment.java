package com.sea.lattice.ui.record;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.ui.DefaultFragmentPagerAdapter;
import com.sea.lattice.ui.Navigator;
import com.sea.lattice.ui.template.TemplateFragment;

/**
 * Created by Sea on 9/26/2015.
 */
public class RecordOverviewFragment extends Fragment implements TemplateFragment.OnTemplateChooseListener {
    private Navigator navigator;
    private DefaultFragmentPagerAdapter mPagerAdapter;
    private RecordFragment recordFragment;
    private TemplateFragment templateFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_record, container, false);
        navigator = (Navigator) rootView.findViewById(R.id.screen_record_pager);
        recordFragment = new RecordFragment();
        templateFragment = new TemplateFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(TemplateFragment.MODE, TemplateFragment.MODE_CHOOSE);
        templateFragment.setArguments(bundle);
        templateFragment.setOnTemplateChooseListener(this);
        mPagerAdapter = new DefaultFragmentPagerAdapter(getChildFragmentManager());
        mPagerAdapter.addFragment(getString(R.string.str_record), recordFragment);
        mPagerAdapter.addFragment(getString(R.string.record_choose_template), templateFragment);
        navigator.setAdapter(mPagerAdapter);
        navigator.setFocusableInTouchMode(false);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("" + resultCode, "" + Activity.RESULT_OK);
        recordFragment.onActivityResult(requestCode, resultCode, data);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void OnChoose(String content) {
        recordFragment.setContent(content);
        navigator.selectBar(0);
    }
}
