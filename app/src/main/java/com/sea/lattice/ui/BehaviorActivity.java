package com.sea.lattice.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;

/**
 * Created by Sea on 9/26/2015.
 */
public class BehaviorActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, BehaviorList.newInstance("",new String[]{})).commit();
    }
}