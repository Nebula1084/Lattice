package com.sea.lattice.ui;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sea.lattice.R;
import com.sea.lattice.content.DirectoryMeta;
import com.sea.lattice.ui.record.DirectoryFragment;
import com.sea.lattice.ui.record.TmpChsFragment;
import com.sea.lattice.widget.ProgressNavigator;

/**
 * Created by Sea on 9/28/2015.
 */
public class TemplateActivity extends FragmentActivity implements View.OnClickListener, DirectoryFragment.OnDirectoryClickListener{
    private ProgressNavigator mProgressNavigator;
    private FragmentManager fragmentManager;
    private Button addButton;
    private ContentResolver contentResolver;
    private DirectoryFragment directoryFragment;
    private TmpChsFragment tmpChsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        fragmentManager=getSupportFragmentManager();
        directoryFragment=new DirectoryFragment();
        directoryFragment.setOnDirectoryClickListener(this);
        fragmentManager.beginTransaction().add(R.id.fragment_container, directoryFragment).commit();
        addButton = (Button)this.findViewById(R.id.add);
        addButton.setOnClickListener(this);
        mProgressNavigator = (ProgressNavigator) this.findViewById(R.id.progress_navigator);
        mProgressNavigator.forword("选择目录");
        contentResolver=getContentResolver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                newDirectory();
                break;
        }
    }

    private void newDirectory(){
        final EditText nameEdit = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新建目录");
        builder.setView(nameEdit);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEdit.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(TemplateActivity.this, "请输入目录名", Toast.LENGTH_LONG).show();
                } else {
                    ContentValues cv=new ContentValues();
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
        mProgressNavigator.forword(cursor.getString(cursor.getColumnIndex(DirectoryMeta.NAME)));
        fragmentManager.beginTransaction().replace(R.id.fragment_container, tmpChsFragment).commit();
    }
}
