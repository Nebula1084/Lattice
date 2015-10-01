package com.sea.lattice.ui.template;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.content.TemplateMeta;

/**
 * Created by Sea on 9/21/2015.
 */
public class TmpChsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter mAdapter;
    private int dirct_id;
    private Bundle bundle;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2,
                null, new String[]{DirectoryMeta.ID, DirectoryMeta.NAME},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(mAdapter);
        bundle = getArguments();
        dirct_id = bundle.getInt(DirectoryMeta.ID);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = TemplateMeta.CONTENT_URI;
        String[] projection = new String[]{TemplateMeta.ID, TemplateMeta.NAME, TemplateMeta.DIRECTORY, TemplateMeta.FREQUENCY};
        return new CursorLoader(getActivity(), baseUri,
                projection, TemplateMeta.DIRECTORY+"=?", new String[]{String.valueOf(dirct_id)}, TemplateMeta.NAME + " ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    public interface OnTemplateSelectedListener {
        void onTempalteSelected(int position);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

    }
}
