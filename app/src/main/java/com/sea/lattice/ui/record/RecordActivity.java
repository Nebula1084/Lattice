package com.sea.lattice.ui.record;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.ui.BehaviorList;


/**
 * Created by Sea on 9/15/2015.
 */
public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private int category;
    private Toolbar toolbar;
    private RecordOverviewFragment recordOverviewFragment;
    BehaviorList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Bundle bundle = getIntent().getExtras();
        category = bundle.getInt(BehaviorMeta.CATEGORY);
        fragmentManager = getSupportFragmentManager();
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recordOverviewFragment = new RecordOverviewFragment();
        switch (category) {
            case BehaviorMeta.WHITE_BEHAVIOR:
                fragmentManager.beginTransaction().add(R.id.fragment_container, recordOverviewFragment).commit();
                break;
            case BehaviorMeta.BLACK_BEHAVIOR:
                fragmentManager.beginTransaction().add(R.id.fragment_container, recordOverviewFragment).commit();
                break;
            case BehaviorMeta.WHITE_COUNTER:
                list = BehaviorList.newInstance(BehaviorMeta.CATEGORY + "=" + BehaviorMeta.WHITE_BEHAVIOR + " AND " + BehaviorMeta.OPP + "=-1", new String[]{});
                fragmentManager.beginTransaction().add(R.id.fragment_container, list).commit();
                break;
            case BehaviorMeta.BALCK_COUNTER:
                list = BehaviorList.newInstance(BehaviorMeta.CATEGORY + "=" + BehaviorMeta.BLACK_BEHAVIOR + " AND " + BehaviorMeta.OPP + "=-1", new String[]{});
                fragmentManager.beginTransaction().add(R.id.fragment_container, list).commit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recordOverviewFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void onBehaviorClick(Cursor cursor) {
        Bundle bundle = getIntent().getExtras();
        int id = cursor.getInt(cursor.getColumnIndex(BehaviorMeta.ID));
        bundle.putInt(BehaviorMeta.ID, id);
        Log.v(BehaviorMeta.ID, String.valueOf(id));
        String content = cursor.getString(cursor.getColumnIndex(BehaviorMeta.CONTENT));
        bundle.putString(BehaviorMeta.CONTENT, content);
        getIntent().putExtras(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new RecordOverviewFragment()).commit();
    }
}