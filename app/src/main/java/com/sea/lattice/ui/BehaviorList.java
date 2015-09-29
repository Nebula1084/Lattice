package com.sea.lattice.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;

import java.util.Date;

/**
 * Created by Sea on 9/24/2015.
 */
public class BehaviorList extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private CursorAdapter mAdapter;
    private static final String SELECTION = "selection";
    private static final String SELECTION_ARGS = "selection_args";
    private String selection;
    private String[] selectionArgs;
    private OnBehaviorClickListner mCallback;

    public interface OnBehaviorClickListner {
        void onBehaviorClick(Cursor cursor);
    }

    public static BehaviorList newInstance(@NonNull String selection, @NonNull String[] selectionArgs) {
        BehaviorList instance = new BehaviorList();
        Bundle args = new Bundle();
        args.putString(SELECTION, selection);
        args.putStringArray(SELECTION_ARGS, selectionArgs);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        selection = bundle.getString(SELECTION);
        selectionArgs = bundle.getStringArray(SELECTION_ARGS);
        mAdapter = new BehaviorCurosrAdapter(getActivity(), null);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (mCallback != null)
            mCallback.onBehaviorClick((Cursor) l.getAdapter().getItem(position));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = BehaviorMeta.CONTENT_URI;
        String[] projection = new String[]{BehaviorMeta.ID, BehaviorMeta.DATE, BehaviorMeta.CATEGORY, BehaviorMeta.CONTENT, BehaviorMeta.OPP};
        return new CursorLoader(getActivity(), baseUri,
                projection, selection, selectionArgs, BehaviorMeta.DATE + " DESC");
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

    private class BehaviorCurosrAdapter extends CursorAdapter {

        public BehaviorCurosrAdapter(Context context, Cursor c) {
            super(context, c, true);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item_behavior, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView item_behvaior_category = (TextView) view.findViewById(R.id.item_behavior_category);
            TextView item_behvaior_date = (TextView) view.findViewById(R.id.item_behavior_date);
            TextView item_behvaior_content = (TextView) view.findViewById(R.id.item_behavior_content);
            Date date = new Date(cursor.getLong(1));
            item_behvaior_category.setText(BehaviorMeta.getCategory(cursor.getInt(2)));
            item_behvaior_date.setText(date.toString());
            item_behvaior_content.setText(cursor.getString(0)+" "+cursor.getString(3)+" "+cursor.getString(4));

        }
    }

    public void setOnBehaviorClickListner(OnBehaviorClickListner callback) {
        this.mCallback = callback;
    }
}