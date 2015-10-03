package com.sea.lattice.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.ui.BehaviorActivity;
import com.sea.lattice.ui.template.TemplateActivity;
import com.sea.lattice.ui.record.RecordActivity;
import com.sea.lattice.ui.template.TemplateFragment;


public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_wht_cnt, btn_blk_cnt, btn_wht_bhv, btn_blk_bhv;
    private Button main_behavior, btn_delete_test, btn_delete_directory, main_template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
