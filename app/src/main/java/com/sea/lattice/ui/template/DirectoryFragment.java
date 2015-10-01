package com.sea.lattice.ui.template;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.sea.lattice.content.DirectoryMeta;

/**
 * Created by Sea on 9/23/2015.
 */
public class DirectoryFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;
    private OnDirectoryClickListener mCallback;

    public interface OnDirectoryClickListener {
        void onDirectoryClickListener(Cursor cursor);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cursor cursor = (Cursor) mAdapter.getItem(position);
        if (mCallback != null)
            mCallback.onDirectoryClickListener(cursor);
    }

    public void setOnDirectoryClickListener(OnDirectoryClickListener callback) {
        mCallback = callback;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2,
                null, new String[]{DirectoryMeta.ID, DirectoryMeta.NAME},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
        setListAdapter(mAdapter);
        getActivity().getContentResolver().registerContentObserver(DirectoryMeta.CONTENT_URI, true, new DirectoryObserver(new Handler()));
        getLoaderManager().initLoader(0, null, this);
    }


    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = DirectoryMeta.CONTENT_URI;
        String[] projection = new String[]{DirectoryMeta.ID, DirectoryMeta.NAME};
        return new CursorLoader(getActivity(), baseUri,
                projection, "", new String[]{}, DirectoryMeta.NAME + " ASC");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);

        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    class DirectoryObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DirectoryObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (isAdded())
                getLoaderManager().getLoader(0).forceLoad();
        }
    }
}
