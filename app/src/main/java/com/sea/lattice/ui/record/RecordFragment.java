package com.sea.lattice.ui.record;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.SnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sea.lattice.R;
import com.sea.lattice.content.BehaviorMeta;
import com.sea.lattice.content.FileManager;
import com.sea.lattice.dao.LatticeDB;
import com.sea.lattice.widget.MaterialPlayPauseButton;
import com.sea.lattice.widget.RecordDialog;
import com.sea.lattice.widget.TimeDialog;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Sea on 9/19/2015.
 */
public class RecordFragment extends Fragment implements View.OnClickListener {
    private TimeDialog timeDialog;
    private TextView record_date_picker, record_behavior_picker;
    private ButtonRectangle frg_rcd_confirm, rcd_photo, rcd_audio;
    private MaterialEditText frg_rcd_content;
    private Bundle bundle;
    private int category;
    private ImageCapture imageCapture;
    private ImageView rcd_photo_image;
    private String photo_src = null, audio_src = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frg_rcd_record, container, false);

        bundle = getActivity().getIntent().getExtras();
        timeDialog = new TimeDialog(getActivity());
        category = bundle.getInt(BehaviorMeta.CATEGORY);
        timeDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                initTimeText();
            }
        });
        record_date_picker = (TextView) rootView.findViewById(R.id.record_date_picker);
        record_date_picker.setOnClickListener(this);
        record_behavior_picker = (TextView) rootView.findViewById(R.id.record_behavior_picker);
        frg_rcd_confirm = (ButtonRectangle) rootView.findViewById(R.id.frg_rcd_confirm);
        frg_rcd_confirm.setOnClickListener(this);
        frg_rcd_content = (MaterialEditText) rootView.findViewById(R.id.frg_rcd_content);
        initTimeText();
        record_behavior_picker.setText(BehaviorMeta.getCategory(category));

        rcd_photo = (ButtonRectangle) rootView.findViewById(R.id.rcd_photo);
        rcd_photo.setOnClickListener(this);

        rcd_audio = (ButtonRectangle) rootView.findViewById(R.id.rcd_audio);
        rcd_audio.setOnClickListener(this);

        rcd_photo_image = (ImageView) rootView.findViewById(R.id.rcd_photo_image);
        imageCapture = new ImageCapture(getContext());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ImageCapture.CAPUTRE_REQ:
                try {
                    rcd_photo_image.setImageBitmap(imageCapture.getImage());
                    photo_src = ImageCapture.TMP_FILE.getPath();
                } catch (IOException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG);
                    e.printStackTrace();
                }
                break;
        }
    }

    public void setContent(String content) {
        frg_rcd_content.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_date_picker:
                timeDialog.show();
                break;
            case R.id.frg_rcd_confirm:
                try {
                    String photo_add= FileManager.newPhoto(photo_src);
                    String audio_add = FileManager.newAudio(audio_src);
                    insert(timeDialog.getDate(), category, frg_rcd_content.getText().toString(), photo_add, audio_add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().finish();
                break;
            case R.id.rcd_photo:
                imageCapture.capture();
                break;
            case R.id.rcd_audio:
                new RecordDialog(getContext()).setOnFinishListener(new RecordDialog.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        audio_src = VoiceRecorder.TMP_FILE.getPath();
                    }
                });
                break;
        }
    }

    private void insert(Date date, int category, String content, String photo, String audio) {
        ContentValues cv = new ContentValues();
        ContentResolver behaviorResolver = getActivity().getContentResolver();
        Uri uri;
        cv.put(BehaviorMeta.DATE, date.getTime());
        cv.put(BehaviorMeta.CATEGORY, category);
        cv.put(BehaviorMeta.CONTENT, content);
        cv.put(BehaviorMeta.PHOTO, photo);
        cv.put(BehaviorMeta.AUDIO, audio);
        switch (category) {
            case BehaviorMeta.WHITE_BEHAVIOR:
            case BehaviorMeta.BLACK_BEHAVIOR:
                cv.put(BehaviorMeta.OPP, -1);
                uri = behaviorResolver.insert(BehaviorMeta.CONTENT_URI, cv);
                break;
            case BehaviorMeta.WHITE_COUNTER:
            case BehaviorMeta.BALCK_COUNTER:
                int opp = bundle.getInt(BehaviorMeta.ID);
                cv.put(BehaviorMeta.OPP, opp);
                uri = behaviorResolver.insert(BehaviorMeta.CONTENT_URI, cv);
                String rowId = uri.getPathSegments().get(1);
                Cursor cursor = behaviorResolver.query(BehaviorMeta.CONTENT_URI, new String[]{BehaviorMeta.ID}, LatticeDB.ROWID + "=?", new String[]{rowId}, "");
                cursor.moveToFirst();
                int newId = cursor.getInt(cursor.getColumnIndex(BehaviorMeta.ID));
                cv = new ContentValues();
                cv.put(BehaviorMeta.OPP, newId);
                behaviorResolver.update(BehaviorMeta.CONTENT_URI, cv, BehaviorMeta.ID + "=?", new String[]{String.valueOf(opp)});
                break;
        }

    }

    private void initTimeText() {
        Date date = timeDialog.getDate();
        record_date_picker.setText((date.getYear() + 1900) + "." + (date.getMonth() + 1) + "." + date.getDate() + " " +
                date.getHours() + ":" + date.getMinutes());
    }

}
