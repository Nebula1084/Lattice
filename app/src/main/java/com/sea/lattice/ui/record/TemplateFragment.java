package com.sea.lattice.ui.record;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sea.lattice.R;
import com.sea.lattice.widget.ProgressNavigator;

/**
 * Created by Sea on 9/19/2015.
 */
public class TemplateFragment extends Fragment implements CategoryFragment.OnCategorySelectedListener, TmpChsFragment.OnTemplateSelectedListener {
    private ProgressNavigator mProgressNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_rcd_tempalte, container, false);
        mProgressNavigator = (ProgressNavigator) rootView.findViewById(R.id.progress_navigator);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mProgressNavigator.forword("选择模板");
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, new DirectoryFragment()).commit();
    }

    @Override
    public void onCategorySelected(int position) {
        mProgressNavigator.forword("选择模板");
        FragmentManager fragmentManager=getChildFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container, new TmpChsFragment()).commit();
    }

    @Override
    public void onTempalteSelected(int position) {

    }
}
