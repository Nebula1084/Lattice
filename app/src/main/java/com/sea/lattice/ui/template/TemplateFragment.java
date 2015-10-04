package com.sea.lattice.ui.template;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.content.TemplateMeta;
import com.sea.lattice.widget.ProgressNavigator;

/**
 * Created by Sea on 9/19/2015.
 */
public class TemplateFragment extends Fragment implements View.OnClickListener, DirectoryFragment.OnDirectoryClickListener, TmpChsFragment.OnTemplateSelectedListener {
    private ProgressNavigator mProgressNavigator;
    private FragmentManager fragmentManager;
    private Button addButton;
    private ContentResolver contentResolver;

    private DirectoryFragment directoryFragment;
    private TmpChsFragment tmpChsFragment;
    private TmpRrcFragment tmpRrcFragment;

    private int status;
    private int mode;
    private BehaviorObservable behaviorObservable;
    private OnTemplateChooseListener mCallback;

    public final static String MODE = "mode";
    public final static int MODE_INSERT = 1;
    public final static int MODE_EDIT = 2;
    public final static int MODE_CHOOSE = 3;

    public final static int STAT_DRCT = 1;
    public final static int STAT_TEMP = 2;
    public final static int STAT_TCHS = 3;

    public interface OnTemplateChooseListener {
        void OnChoose(String content);
    }


    public TemplateFragment() {
        tmpChsFragment = new TmpChsFragment();
        tmpRrcFragment = new TmpRrcFragment();
        behaviorObservable = new BehaviorObservable();
        behaviorObservable.addObserver(tmpChsFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_template, container, false);
        fragmentManager = getChildFragmentManager();
        directoryFragment = new DirectoryFragment();
        directoryFragment.setOnDirectoryClickListener(this);
        Bundle bundle = getArguments();
        mode = bundle.getInt(MODE);
        replaceFragment(directoryFragment, STAT_DRCT);
        addButton = (Button) rootView.findViewById(R.id.add);
        addButton.setOnClickListener(this);
        if (mode == MODE_CHOOSE)
            addButton.setVisibility(View.GONE);
        mProgressNavigator = (ProgressNavigator) rootView.findViewById(R.id.progress_navigator);
        mProgressNavigator.forword("选择目录", new ProgressNavigator.OnBarClickListener(mProgressNavigator) {
            public void onClick(View v) {
                super.onClick(v);
                replaceFragment(directoryFragment, STAT_DRCT);
            }
        });
        contentResolver = getActivity().getContentResolver();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public void setOnTemplateChooseListener(OnTemplateChooseListener callback) {
        this.mCallback = callback;
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
                        tmpRrcFragment.setMode(TemplateFragment.MODE_INSERT);
                        mProgressNavigator.forword("新建模板", new ProgressNavigator.OnBarClickListener(mProgressNavigator) {
                            public void onClick(View v) {
                                super.onClick(v);
                                tmpRrcFragment.setMode(TemplateFragment.MODE_INSERT);
                                replaceFragment(tmpRrcFragment, STAT_TCHS);
                            }
                        });
                        tmpRrcFragment.setArguments(tmpChsFragment.getArguments());
                        replaceFragment(tmpRrcFragment, STAT_TCHS);
                        break;
                    case STAT_TCHS:
                        switch (tmpRrcFragment.getMode()) {
                            case TemplateFragment.MODE_INSERT:
                                if (insertTemplate(tmpRrcFragment.getName(), tmpRrcFragment.getCategory(), tmpRrcFragment.getContent(), tmpRrcFragment.getDirectory())) {
                                    replaceFragment(tmpChsFragment, STAT_TEMP);
                                    mProgressNavigator.pop();
                                }
                                break;
                            case TemplateFragment.MODE_EDIT:
                                if (updateTempate(tmpRrcFragment.getTempId(), tmpRrcFragment.getName(), tmpRrcFragment.getCategory(), tmpRrcFragment.getContent())) {
                                    replaceFragment(tmpChsFragment, STAT_TEMP);
                                    mProgressNavigator.pop();
                                }
                                break;
                        }
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
        tmpChsFragment.setOnTemplateSelectedLIstener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(TemplateMeta.DIRECTORY, cursor.getInt(cursor.getColumnIndex(DirectoryMeta.ID)));
        tmpChsFragment.setArguments(bundle);
        mProgressNavigator.forword(cursor.getString(cursor.getColumnIndex(DirectoryMeta.NAME)),
                new ProgressNavigator.OnBarClickListener(mProgressNavigator) {
                    public void onClick(View v) {
                        super.onClick(v);
                        replaceFragment(tmpChsFragment, STAT_TEMP);
                    }
                });
        replaceFragment(tmpChsFragment, STAT_TEMP);
        chooseBehavior(cursor.getInt(cursor.getColumnIndex(DirectoryMeta.ID)), getActivity().getIntent().getExtras().getInt(BehaviorMeta.CATEGORY));
    }

    private boolean insertTemplate(String name, int category, String content, int directory) {
        if (name.equals("")) {
            Toast.makeText(getActivity(), "名称不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (content.equals("")) {
            Toast.makeText(getActivity(), "内容不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(TemplateMeta.NAME, name);
        cv.put(TemplateMeta.CATEGORY, category);
        cv.put(TemplateMeta.CONTENT, content);
        cv.put(TemplateMeta.DIRECTORY, directory);
        contentResolver.insert(TemplateMeta.CONTENT_URI, cv);
        return true;
    }

    private boolean updateTempate(int id, String name, int category, String content) {
        if (name.equals("")) {
            Toast.makeText(getActivity(), "名称不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        if (content.equals("")) {
            Toast.makeText(getActivity(), "内容不能为空", Toast.LENGTH_LONG).show();
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(TemplateMeta.NAME, name);
        cv.put(TemplateMeta.CATEGORY, category);
        cv.put(TemplateMeta.CONTENT, content);
        Log.v("id", String.valueOf(id));
        contentResolver.update(TemplateMeta.CONTENT_URI, cv, TemplateMeta.ID + "=?", new String[]{String.valueOf(id)});
        return true;
    }

    private void replaceFragment(Fragment fragment, int status) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        this.status = status;
    }

    @Override
    public void onSelect(Cursor cursor) {
        switch (mode) {
            case MODE_EDIT:
                Bundle bundle = new Bundle();
                bundle.putInt(TemplateMeta.ID, cursor.getInt(cursor.getColumnIndex(TemplateMeta.ID)));
                bundle.putString(TemplateMeta.NAME, cursor.getString(cursor.getColumnIndex(TemplateMeta.NAME)));
                bundle.putInt(TemplateMeta.CATEGORY, cursor.getInt(cursor.getColumnIndex(TemplateMeta.CATEGORY)));
                bundle.putString(TemplateMeta.CONTENT, cursor.getString(cursor.getColumnIndex(TemplateMeta.CONTENT)));
                tmpRrcFragment.setArguments(bundle);
                tmpRrcFragment.setMode(TemplateFragment.MODE_EDIT);
                replaceFragment(tmpRrcFragment, STAT_TCHS);
                mProgressNavigator.forword(cursor.getString(cursor.getColumnIndex(TemplateMeta.NAME)),
                        new ProgressNavigator.OnBarClickListener(mProgressNavigator) {
                            public void onClick(View v) {
                                super.onClick(v);
                                tmpRrcFragment.setMode(TemplateFragment.MODE_EDIT);
                                replaceFragment(tmpRrcFragment, STAT_TCHS);
                            }
                        });
                break;
            case MODE_CHOOSE:
                String content = cursor.getString(cursor.getColumnIndex(TemplateMeta.CONTENT));
                if (mCallback != null)
                    mCallback.OnChoose(content);
                break;
        }

    }

    public void chooseBehavior(int directory, int category) {
        behaviorObservable.chooseBehavior(directory, category);
    }
}
