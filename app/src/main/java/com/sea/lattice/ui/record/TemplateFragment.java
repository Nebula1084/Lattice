package com.sea.lattice.ui.record;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sea.lattice.R;
import com.sea.lattice.widget.ProgressNavigator;

/**
 * Created by Sea on 9/19/2015.
 */
public class TemplateFragment extends Fragment {
    private ProgressNavigator mProgressNavigator;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_rcd_tempalte, container, false);
        button = (Button) rootView.findViewById(R.id.button);
        mProgressNavigator = (ProgressNavigator) rootView.findViewById(R.id.progress_navigator);
        mProgressNavigator.forword("选择模板");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressNavigator.forword("第一模板");
            }
        });
        return rootView;
    }
}
