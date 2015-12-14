package com.sea.lattice.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.SnackBar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.dao.LatticeDB;
import com.sea.lattice.ui.record.RecordActivity;
import com.sea.lattice.ui.template.TemplateActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ButtonRectangle btn_wht_cnt, btn_blk_cnt, btn_wht_bhv, btn_blk_bhv;
    private Toolbar toolbar;
    private LinearLayout main_remind, main_export, main_about, main_encouterremind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        btn_wht_cnt = (ButtonRectangle) this.findViewById(R.id.btn_wht_cnt);
        btn_wht_cnt.setOnClickListener(new RecordListener(this));
        btn_blk_cnt = (ButtonRectangle) this.findViewById(R.id.btn_blk_cnt);
        btn_blk_cnt.setOnClickListener(new RecordListener(this));
        btn_wht_bhv = (ButtonRectangle) this.findViewById(R.id.btn_wht_bhv);
        btn_wht_bhv.setOnClickListener(new RecordListener(this));
        btn_blk_bhv = (ButtonRectangle) this.findViewById(R.id.btn_blk_bhv);
        btn_blk_bhv.setOnClickListener(new RecordListener(this));

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.avoscloud_search_result_open_background)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Mike Penz")
                                .withEmail("mikepenz@gmail.com")
                                .withIcon(getResources().getDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        final Drawer result = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("模板"),
                        new PrimaryDrawerItem().withName("记录"),
                        new PrimaryDrawerItem().withName("统计"),
                        new DividerDrawerItem()
                )
                .build();
        result.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch (position) {
                    case 1:
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt(BehaviorMeta.CATEGORY, BehaviorMeta.ALL);
                        intent.putExtras(bundle);
                        intent.setClass(MainActivity.this, TemplateActivity.class);
                        startActivity(intent);
                        result.closeDrawer();
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, BehaviorActivity.class));
                        result.closeDrawer();
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, StatisticActivity.class));
                        result.closeDrawer();
                        break;
                }
                return true;
            }
        });

        /* slide menu*/
        main_remind = (LinearLayout) this.findViewById(R.id.main_remind);
        main_remind.setOnClickListener(this);

        main_export = (LinearLayout) this.findViewById(R.id.main_export);
        main_export.setOnClickListener(this);
        main_about = (LinearLayout) this.findViewById(R.id.main_about);
        main_about.setOnClickListener(this);
        main_encouterremind = (LinearLayout) this.findViewById(R.id.main_encouterremind);
        main_encouterremind.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.main_initial:
                LatticeDB db = new LatticeDB(this);
                db.initialize();
                break;
            case R.id.main_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_remind:
                startActivity(new Intent(this, RemindActivity.class));
                break;
            case R.id.main_encouterremind:
                startActivity(new Intent(this, CounterRemindActivity.class));
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
