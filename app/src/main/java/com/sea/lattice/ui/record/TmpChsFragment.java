package com.sea.lattice.ui.record;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sea.lattice.R;

/**
 * Created by Sea on 9/21/2015.
 */
public class TmpChsFragment extends ListFragment {
    OnTemplateSelectedListener mCallback;

    public interface OnTemplateSelectedListener {
        void onTempalteSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_rcd_category, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        mCallback.onTempalteSelected(position);
    }
}
