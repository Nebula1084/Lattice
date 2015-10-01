package com.sea.lattice.ui.template;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.DirectoryMeta;

/**
 * Created by Sea on 9/30/2015.
 */
public class TmpRrcFragment extends Fragment implements View.OnClickListener {
    private int dirct_id;
    private int category;
    private TextView record_template_picker;
    private EditText frg_tmp_name, frg_tmp_content;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_tmp_record, container, false);
        dirct_id = getArguments().getInt(DirectoryMeta.ID);
        record_template_picker = (TextView) rootView.findViewById(R.id.record_template_picker);
        record_template_picker.setOnClickListener(this);
        frg_tmp_content = (EditText) rootView.findViewById(R.id.frg_tmp_content);
        frg_tmp_name = (EditText) rootView.findViewById(R.id.frg_tmp_name);
        category = 0;
        record_template_picker.setText(BehaviorMeta.getCategory(category));
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_template_picker:
                CategoryPicker();
                break;
        }
    }

    public String getName(){
        return frg_tmp_name.getText().toString();
    }

    public String getContent() {
        return frg_tmp_content.getText().toString();
    }

    public int getCategory() {
        return category;
    }

    public int getDirectory(){
        return dirct_id;
    }

    public void CategoryPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("选择业习");
        builder.setSingleChoiceItems(
                new String[]{BehaviorMeta.getCategory(0), BehaviorMeta.getCategory(1), BehaviorMeta.getCategory(2), BehaviorMeta.getCategory(3)},
                category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        category = which;
                        record_template_picker.setText(BehaviorMeta.getCategory(category));
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}
