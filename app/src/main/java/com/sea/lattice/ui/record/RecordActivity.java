package com.sea.lattice.ui.record;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.widget.TimeDialog;


/**
 * Created by Sea on 9/15/2015.
 */
public class RecordActivity extends Activity implements View.OnClickListener{

    private TextView record_date_picker;
    private TimeDialog timeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        timeDialog = new TimeDialog(this);
        record_date_picker = (TextView)this.findViewById(R.id.record_date_picker);
        record_date_picker.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.record_date_picker:
                timeDialog.show();
                break;
        }
    }
}
