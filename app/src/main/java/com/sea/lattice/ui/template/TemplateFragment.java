package com.sea.lattice.ui.template;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sea.lattice.R;
import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.content.TemplateMeta;
import com.sea.lattice.widget.ProgressNavigator;

/**
 * Created by Sea on 9/19/2015.
 */
public class TemplateFragment extends Fragment implements View.OnClickListener, DirectoryFragment.OnDirectoryClickListener{
    private ProgressNavigator mProgressNavigator;
    private FragmentManager fragmentManager;
    private Button addButton;
    private ContentResolver contentResolver;
    private DirectoryFragment directoryFragment;
    private TmpChsFragment tmpChsFragment;
    private TmpRrcFragment tmpRrcFragment;
    private int status;

    public final static int STAT_DRCT = 1;
    public final static int STAT_TEMP = 2;
    public final static int STAT_TCHS = 3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_template, container, false);
        status = STAT_DRCT;
        fragmentManager = getChildFragmentManager();
        directoryFragment = new DirectoryFragment();
        directoryFragment.setOnDirectoryClickListener(this);
        fragmentManager.beginTransaction().add(R.id.fragment_container, directoryFragment).commit();
        addButton = (Button) rootView.findViewById(R.id.add);
        addButton.setOnClickListener(this);
        mProgressNavigator = (ProgressNavigator) rootView.findViewById(R.id.progress_navigator);
        mProgressNavigator.forword("选择目录", new ProgressNavigator.OnBarClickListener(mProgressNavigator) {
            public void onClick(View v) {
                super.onClick(v);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, directoryFragment).commit();
                status = STAT_DRCT;
            }
        });
        contentResolver = getActivity().getContentResolver();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                switch (status) {
                    case STAT_DRCT:
                        newDirectory();
                        break;
                    case STAT_TEMP:
                        tmpRrcFragment = new TmpRrcFragment();
                        mProgressNavigator.forword("新建模板",new ProgressNavigator.OnBarClickListener(mProgressNavigator) {
                            public void onClick(View v) {
                                super.onClick(v);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, tmpRrcFragment).commit();
                                status = STAT_TCHS;
                            }
                        });
                        tmpRrcFragment.setArguments(tmpChsFragment.getArguments());
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, tmpRrcFragment).commit();
                        status = STAT_TCHS;
                        break;
                    case STAT_TCHS:
                        insertTemplate(tmpRrcFragment.getName(), tmpRrcFragment.getCategory(), tmpRrcFragment.getContent(), tmpRrcFragment.getDirectory());
                        break;
                }
                break;
        }
    }

    private void newDirectory() {
        final EditText nameEdit = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("新建目录");
        builder.setView(nameEdit);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEdit.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(getActivity(), "请输入目录名", Toast.LENGTH_LONG).show();
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put(DirectoryMeta.NAME, name);
                    contentResolver.insert(DirectoryMeta.CONTENT_URI, cv);
                    dialog.cancel();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onDirectoryClickListener(Cursor cursor) {
        tmpChsFragment = new TmpChsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DirectoryMeta.ID, cursor.getInt(cursor.getColumnIndex(DirectoryMeta.ID)));
        tmpChsFragment.setArguments(bundle);
        mProgressNavigator.forword(cursor.getString(cursor.getColumnIndex(DirectoryMeta.NAME)),
                new ProgressNavigator.OnBarClickListener(mProgressNavigator) {
                    public void onClick(View v) {
                        super.onClick(v);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, tmpChsFragment).commit();
                        status = STAT_TEMP;
                    }
                });
        fragmentManager.beginTransaction().replace(R.id.fragment_container, tmpChsFragment).commit();
        status = STAT_TEMP;
    }

    private void insertTemplate(String name, int category, String content, int directory) {
        ContentValues cv = new ContentValues();
        cv.put(TemplateMeta.NAME, name);
        cv.put(TemplateMeta.CATEGORY, category);
        cv.put(TemplateMeta.CONTENT, content);
        cv.put(TemplateMeta.DIRECTORY, directory);
        contentResolver.insert(TemplateMeta.CONTENT_URI, cv);
    }
}
