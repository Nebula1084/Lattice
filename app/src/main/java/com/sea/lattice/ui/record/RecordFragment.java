package com.sea.lattice.ui.record;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.widget.TimeDialog;

/**
 * Created by Sea on 9/19/2015.
 */
public class RecordFragment extends Fragment {
    private TimeDialog timeDialog;
    private TextView record_date_picker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_record, container, false);
        timeDialog = new TimeDialog(getActivity());
        record_date_picker = (TextView)rootView.findViewById(R.id.record_date_picker);
        record_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog.show();
            }
        });
        return rootView;
    }
}
