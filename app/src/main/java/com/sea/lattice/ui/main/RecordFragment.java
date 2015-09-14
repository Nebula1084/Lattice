package com.sea.lattice.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sea on 9/13/2015.
 */
public class RecordFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        TextView textView = new TextView(getActivity());
        textView.setText("text");
        return textView;
    }
}
