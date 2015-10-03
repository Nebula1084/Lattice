package com.sea.lattice.ui.template;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.sea.lattice.R;

/**
 * Created by Sea on 9/28/2015.
 */
public class TemplateActivity extends FragmentActivity  {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_container);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new TemplateFragment()).commit();
    }

}