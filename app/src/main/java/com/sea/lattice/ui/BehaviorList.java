package com.sea.lattice.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dexafree.materialList.view.MaterialListView;
import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.widget.LatticeCard;

import java.util.Date;

/**
 * Created by Sea on 9/24/2015.
 */
public class BehaviorList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String SELECTION = "selection";
    private static final String SELECTION_ARGS = "selection_args";
    private String selection;
    private String[] selectionArgs;
    private MaterialListView materialListView;

    public static BehaviorList newInstance(@NonNull String selection, @NonNull String[] selectionArgs) {
        BehaviorList instance = new BehaviorList();
        Bundle args = new Bundle();
        args.putString(SELECTION, selection);
        args.putStringArray(SELECTION_ARGS, selectionArgs);
        instance.setArguments(args);
        return instance;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_behavior_list, container, false);
        materialListView = (MaterialListView) rootView.findViewById(R.id.material_listview);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        selection = bundle.getString(SELECTION);
        selectionArgs = bundle.getStringArray(SELECTION_ARGS);
        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = BehaviorMeta.CONTENT_URI;
        String[] projection = new String[]{
                BehaviorMeta.ID, BehaviorMeta.DATE, BehaviorMeta.CATEGORY,
                BehaviorMeta.CONTENT, BehaviorMeta.OPP,
                BehaviorMeta.AUDIO, BehaviorMeta.PHOTO
        };
        return new CursorLoader(getActivity(), baseUri,
                projection, selection, selectionArgs, BehaviorMeta.DATE + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        materialListView.clear();
        LatticeCard latticeCard;
        if (data.moveToFirst()){
            do {
                latticeCard = new LatticeCard(getContext());
                latticeCard.setDate(new Date(data.getLong(1)));
                latticeCard.setBehavior(BehaviorMeta.getCategory(data.getInt(2)));
                latticeCard.setContent(data.getString(3));
                latticeCard.setAudio(data.getString(5));
                latticeCard.setPhoto(data.getString(6));
                materialListView.add(latticeCard);
            }while (data.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}