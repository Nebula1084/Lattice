package com.sea.lattice.ui.template;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.sea.lattice.R;

/**
 * Created by Sea on 9/28/2015.
 */
public class TemplateActivity extends FragmentActivity  {
    private FragmentManager fragmentManager;
    private TemplateFragment templateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_container);
        fragmentManager = getSupportFragmentManager();
        templateFragment = new TemplateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TemplateFragment.MODE, TemplateFragment.MODE_EDIT);
        templateFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, templateFragment).commit();
    }

}