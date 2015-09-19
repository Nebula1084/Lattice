package com.sea.lattice.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sea.lattice.R;
import com.sea.lattice.dao.behavior.CounterBehavior;
import com.sea.lattice.dao.behavior.OriginBehavior;
import com.sea.lattice.ui.record.RecordActivity;


public class MainActivity extends Activity {
    private Button btn_wht_cnt, btn_blk_cnt, btn_wht_bhv, btn_blk_bhv;

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
                    startRecord(CounterBehavior.WHITE_COUNTER);
                    break;
                case R.id.btn_blk_cnt:
                    startRecord(CounterBehavior.BALCK_COUNTER);
                    break;
                case R.id.btn_wht_bhv:
                    startRecord(OriginBehavior.WHITE_BEHAVIOR);
                    break;
                case R.id.btn_blk_bhv:
                    startRecord(OriginBehavior.BLACK_BEHAVIOR);
                    break;
            }
        }

        private void startRecord(int category) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("category", category);
            intent.putExtras(bundle);
            intent.setClass(context, RecordActivity.class);
            startActivity(intent);
        }
    }
}
