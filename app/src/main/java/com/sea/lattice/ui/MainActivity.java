package com.sea.lattice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.ui.record.RecordActivity;
import com.sea.lattice.ui.template.TemplateActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_wht_cnt, btn_blk_cnt, btn_wht_bhv, btn_blk_bhv;
    private Button main_behavior, btn_delete_test, btn_delete_directory, main_template;
    private Button main_statistics;
    private Toolbar toolbar;
    private DrawerLayout lattice_drawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout main_remind, main_export, main_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        /* main screen*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_wht_cnt = (Button) this.findViewById(R.id.btn_wht_cnt);
        btn_wht_cnt.setOnClickListener(new RecordListener(this));
        btn_blk_cnt = (Button) this.findViewById(R.id.btn_blk_cnt);
        btn_blk_cnt.setOnClickListener(new RecordListener(this));
        btn_wht_bhv = (Button) this.findViewById(R.id.btn_wht_bhv);
        btn_wht_bhv.setOnClickListener(new RecordListener(this));
        btn_blk_bhv = (Button) this.findViewById(R.id.btn_blk_bhv);
        btn_blk_bhv.setOnClickListener(new RecordListener(this));
        main_behavior = (Button) this.findViewById(R.id.main_behavior);
        main_behavior.setOnClickListener(this);
        main_template = (Button) this.findViewById(R.id.main_template);
        main_template.setOnClickListener(this);
        btn_delete_test = (Button) this.findViewById(R.id.btn_delete_test);
        btn_delete_test.setOnClickListener(this);
        btn_delete_directory = (Button) this.findViewById(R.id.btn_delete_directory);
        btn_delete_directory.setOnClickListener(this);
        main_statistics = (Button) this.findViewById(R.id.main_statistics);
        main_statistics.setOnClickListener(this);
        lattice_drawer = (DrawerLayout) this.findViewById(R.id.lattice_drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, lattice_drawer, toolbar, R.string.open, R.string.close);
        mDrawerToggle.syncState();
        lattice_drawer.setDrawerListener(mDrawerToggle);

        /* slide menu*/
        main_remind = (LinearLayout) this.findViewById(R.id.main_remind);
        main_remind.setOnClickListener(this);
        main_export = (LinearLayout) this.findViewById(R.id.main_export);
        main_export.setOnClickListener(this);
        main_about = (LinearLayout) this.findViewById(R.id.main_about);
        main_about.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_behavior:
                startActivity(new Intent(this, BehaviorActivity.class));
                break;
            case R.id.main_template:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt(BehaviorMeta.CATEGORY, BehaviorMeta.ALL);
                intent.putExtras(bundle);
                intent.setClass(this, TemplateActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_delete_test:
                getContentResolver().delete(BehaviorMeta.CONTENT_URI, "", new String[]{});
                break;
            case R.id.btn_delete_directory:
                getContentResolver().delete(DirectoryMeta.CONTENT_URI, "", new String[]{});
                break;
            case R.id.main_statistics:
                startActivity(new Intent(this, StatisticActivity.class));
                break;
            case R.id.main_remind:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.main_export:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.main_about:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
    }

    class RecordListener implements View.OnClickListener {
        Context context;

        public RecordListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_wht_cnt:
                    startRecord(BehaviorMeta.WHITE_COUNTER);
                    break;
                case R.id.btn_blk_cnt:
                    startRecord(BehaviorMeta.BALCK_COUNTER);
                    break;
                case R.id.btn_wht_bhv:
                    startRecord(BehaviorMeta.WHITE_BEHAVIOR);
                    break;
                case R.id.btn_blk_bhv:
                    startRecord(BehaviorMeta.BLACK_BEHAVIOR);
                    break;
            }
        }

        private void startRecord(int category) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(BehaviorMeta.CATEGORY, category);
            intent.putExtras(bundle);
            intent.setClass(context, RecordActivity.class);
            startActivity(intent);
        }
    }
}
