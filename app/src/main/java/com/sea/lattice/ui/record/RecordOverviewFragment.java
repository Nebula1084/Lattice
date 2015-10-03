package com.sea.lattice.ui.record;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class RecordOverviewFragment extends Fragment {
    private Navigator navigator;
    private DefaultFragmentPagerAdapter mPagerAdapter;
    private Bundle bundle;
    private RecordFragment recordFragment;
    private TemplateFragment templateFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.screen_record, container, false);
        bundle = getActivity().getIntent().getExtras();
        navigator = (Navigator) rootView.findViewById(R.id.screen_record_pager);
        recordFragment = new RecordFragment();
        templateFragment = new TemplateFragment();

        mPagerAdapter = new DefaultFragmentPagerAdapter(getChildFragmentManager());
        mPagerAdapter.addFragment(getString(R.string.str_record), recordFragment);
        mPagerAdapter.addFragment(getString(R.string.record_choose_template), templateFragment);
        navigator.setAdapter(mPagerAdapter);
        navigator.setFocusableInTouchMode(false);

        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
