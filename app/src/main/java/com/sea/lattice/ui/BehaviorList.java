package com.sea.lattice.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.dao.behavior.Behavior;
import com.sea.lattice.dao.behavior.BehaviorPool;
import com.sea.lattice.dao.behavior.OrgWhtBehavior;

import java.util.Date;

/**
 * Created by Sea on 9/24/2015.
 */
public class BehaviorList extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    CursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new BehaviorCurosrAdapter(this, null);
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri = BehaviorMeta.CONTENT_URI;
        String[] projection = new String[]{BehaviorMeta.ID, BehaviorMeta.CATEGORY, BehaviorMeta.CONTENT};

        return new CursorLoader(this, baseUri,
                projection, "", new String[]{}, "");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.v("data", String.valueOf(data.moveToFirst()));
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    private class BehaviorCurosrAdapter extends CursorAdapter{

        public BehaviorCurosrAdapter(Context context, Cursor c) {
            super(context, c, true);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item_behavior, null);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView item_behvaior_category = (TextView)view.findViewById(R.id.item_behavior_category);
            TextView item_behvaior_date = (TextView)view.findViewById(R.id.item_behavior_date);
            TextView item_behvaior_content = (TextView)view.findViewById(R.id.item_behavior_content);

            Date date = new Date(cursor.getLong(1));
            item_behvaior_category.setText(BehaviorMeta.getCategory(cursor.getInt(2)));
            item_behvaior_date.setText(date.toString());
            item_behvaior_content.setText(cursor.getString(3));

        }
    }
}
