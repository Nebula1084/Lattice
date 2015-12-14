package com.sea.lattice.ui.template;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.TemplateMeta;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Sea on 9/21/2015.
 */
public class TmpChsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>, Observer {
    private CursorAdapter mAdapter;
    private int dirct_id;
    private Bundle bundle;
    private OnTemplateSelectedListener mCallback;
    private int mode = TemplateFragment.MODE_EDIT;
    private String selection = null;
    private String[] selectionArgs = null;

    public interface OnTemplateSelectedListener {
        void onSelect(Cursor cursor);
    }

    public void setOnTemplateSelectedLIstener(OnTemplateSelectedListener onTemplateSelectedLIstener) {
        mCallback = onTemplateSelectedLIstener;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        bundle = getArguments();
        dirct_id = bundle.getInt(TemplateMeta.DIRECTORY);
        if (selection == null) {
            selection = TemplateMeta.DIRECTORY + "=?";
            selectionArgs = new String[]{String.valueOf(dirct_id)};
        }
        mAdapter = new TemplateCursorAdapter(getActivity(), null);
        setListAdapter(mAdapter);
        getListView().setPadding(20, 10, 20, 10);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = TemplateMeta.CONTENT_URI;
        String[] projection = new String[]{TemplateMeta.ID, TemplateMeta.NAME, TemplateMeta.CATEGORY, TemplateMeta.CONTENT, TemplateMeta.DIRECTORY, TemplateMeta.FREQUENCY};
        return new CursorLoader(getActivity(), baseUri,
                projection, selection, selectionArgs, TemplateMeta.NAME + " ASC");
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


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        mCallback.onSelect(cursor);
    }

    @Override
    public void update(Observable observable, Object data) {
        BehaviorObservable behaviorObservable = (BehaviorObservable) observable;
        dirct_id = behaviorObservable.getDirectory();
        Log.v("directory", String.valueOf(dirct_id));
        switch (behaviorObservable.getCategory()) {
            case BehaviorMeta.ALL:
                selection = TemplateMeta.DIRECTORY + "=?";
                selectionArgs = new String[]{String.valueOf(dirct_id)};
                break;
            case BehaviorMeta.WHITE_BEHAVIOR:
                selection = TemplateMeta.DIRECTORY + "=? and " + TemplateMeta.CATEGORY + "=?";
                selectionArgs = new String[]{String.valueOf(dirct_id), String.valueOf(BehaviorMeta.WHITE_BEHAVIOR)};
                break;
            case BehaviorMeta.BLACK_BEHAVIOR:
                selection = TemplateMeta.DIRECTORY + "=? and " + TemplateMeta.CATEGORY + "=?";
                selectionArgs = new String[]{String.valueOf(dirct_id), String.valueOf(BehaviorMeta.BLACK_BEHAVIOR)};
                break;
            case BehaviorMeta.WHITE_COUNTER:
                selection = TemplateMeta.DIRECTORY + "=? and " + TemplateMeta.CATEGORY + "=?";
                selectionArgs = new String[]{String.valueOf(dirct_id), String.valueOf(BehaviorMeta.WHITE_COUNTER)};
                break;
            case BehaviorMeta.BALCK_COUNTER:
                selection = TemplateMeta.DIRECTORY + "=? and " + TemplateMeta.CATEGORY + "=?";
                selectionArgs = new String[]{String.valueOf(dirct_id), String.valueOf(BehaviorMeta.BALCK_COUNTER)};
                break;
        }
        if (isAdded()) {
            getLoaderManager().initLoader(0, null, this);
        }
    }

    private class TemplateCursorAdapter extends CursorAdapter {

        public TemplateCursorAdapter(Context context, Cursor c) {
            super(context, c, true);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            TextView text2 = (TextView) view.findViewById(android.R.id.text2);
            text1.setText(cursor.getString(cursor.getColumnIndex(TemplateMeta.NAME)) + " " + BehaviorMeta.getCategory(cursor.getInt(cursor.getColumnIndex(TemplateMeta.CATEGORY))));
            text2.setText(cursor.getString(cursor.getColumnIndex(TemplateMeta.CONTENT)));
        }
    }
}