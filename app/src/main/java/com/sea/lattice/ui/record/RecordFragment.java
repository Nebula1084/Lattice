package com.sea.lattice.ui.record;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.widget.TimeDialog;

/**
 * Created by Sea on 9/19/2015.
 */
public class RecordFragment extends Fragment {
    private TimeDialog timeDialog;
    private Dialog behaviorDialog;
    private TextView record_date_picker, record_behavior_picker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_rcd_record, container, false);
        timeDialog = new TimeDialog(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.str_behavior_choose));
        builder.setSingleChoiceItems(
                new String[]{BehaviorMeta.getCategory(0), BehaviorMeta.getCategory(1), BehaviorMeta.getCategory(2), BehaviorMeta.getCategory(3)},
                0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        behaviorDialog = builder.create();

        record_date_picker = (TextView) rootView.findViewById(R.id.record_date_picker);
        record_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });
        record_behavior_picker = (TextView) rootView.findViewById(R.id.record_behavior_picker);
        record_behavior_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behaviorDialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
