package com.sea.lattice.ui.record;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.dao.LatticeDB;
import com.sea.lattice.widget.TimeDialog;

import java.util.Date;

/**
 * Created by Sea on 9/19/2015.
 */
public class RecordFragment extends Fragment implements View.OnClickListener {
    private TimeDialog timeDialog;
    private TextView record_date_picker, record_behavior_picker;
    private Button frg_rcd_confirm, frg_rcd_cancel;
    private EditText frg_rcd_content;
    private Bundle bundle;
    private int category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_rcd_record, container, false);
        bundle = getActivity().getIntent().getExtras();
        timeDialog = new TimeDialog(getActivity());
        category = bundle.getInt(BehaviorMeta.CATEGORY);
        timeDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                initTimeText();
            }
        });
        record_date_picker = (TextView) rootView.findViewById(R.id.record_date_picker);
        record_date_picker.setOnClickListener(this);
        record_behavior_picker = (TextView) rootView.findViewById(R.id.record_behavior_picker);
        frg_rcd_confirm = (Button) rootView.findViewById(R.id.frg_rcd_confirm);
        frg_rcd_confirm.setOnClickListener(this);
        frg_rcd_cancel = (Button) rootView.findViewById(R.id.frg_rcd_cancel);
        frg_rcd_cancel.setOnClickListener(this);
        frg_rcd_content = (EditText) rootView.findViewById(R.id.frg_rcd_content);
        initTimeText();
        record_behavior_picker.setText(BehaviorMeta.getCategory(category));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_date_picker:
                timeDialog.show();
                break;
            case R.id.frg_rcd_confirm:
                insert(timeDialog.getDate(), category, frg_rcd_content.getText().toString());
                getActivity().finish();
                break;
            case R.id.frg_rcd_cancel:
                getActivity().finish();
                break;
        }
    }

    private void insert(Date date, int category, String content) {
        ContentValues cv = new ContentValues();
        ContentResolver behaviorResolver = getActivity().getContentResolver();
        Uri uri;
        cv.put(BehaviorMeta.DATE, date.getTime());
        cv.put(BehaviorMeta.CATEGORY, category);
        cv.put(BehaviorMeta.CONTENT, content);
        switch (category) {
            case BehaviorMeta.WHITE_BEHAVIOR:
            case BehaviorMeta.BLACK_BEHAVIOR:
                cv.put(BehaviorMeta.OPP, -1);
                uri = behaviorResolver.insert(BehaviorMeta.CONTENT_URI, cv);
                break;
            case BehaviorMeta.WHITE_COUNTER:
            case BehaviorMeta.BALCK_COUNTER:
                int opp = bundle.getInt(BehaviorMeta.ID);
                Log.v(BehaviorMeta.ID, String.valueOf(opp));
                cv.put(BehaviorMeta.OPP, opp);
                uri = behaviorResolver.insert(BehaviorMeta.CONTENT_URI, cv);
                String rowId = uri.getPathSegments().get(1);
                Cursor cursor = behaviorResolver.query(BehaviorMeta.CONTENT_URI, new String[]{BehaviorMeta.ID}, LatticeDB.ROWID + "=?", new String[]{rowId}, "");
                cursor.moveToFirst();
                int newId = cursor.getInt(cursor.getColumnIndex(BehaviorMeta.ID));
                cv = new ContentValues();
                cv.put(BehaviorMeta.OPP, newId);
                behaviorResolver.update(BehaviorMeta.CONTENT_URI, cv, BehaviorMeta.ID + "=?", new String[]{String.valueOf(opp)});
                break;
        }

    }

    private void initTimeText() {
        Date date = timeDialog.getDate();
        record_date_picker.setText((date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate() + " " +
                date.getHours() + ":" + date.getMinutes());
    }
}
