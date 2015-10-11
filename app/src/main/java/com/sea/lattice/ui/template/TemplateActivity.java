package com.sea.lattice.ui.template;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sea.lattice.R;

/**
 * Created by Sea on 9/28/2015.
 */
public class TemplateActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private TemplateFragment templateFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        fragmentManager = getSupportFragmentManager();
        templateFragment = new TemplateFragment();
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        Bundle bundle = new Bundle();
        bundle.putInt(TemplateFragment.MODE, TemplateFragment.MODE_EDIT);
        templateFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, templateFragment).commit();
    }

}