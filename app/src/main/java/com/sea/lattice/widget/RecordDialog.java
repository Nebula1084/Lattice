package com.sea.lattice.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sea.lattice.R;
import com.sea.lattice.ui.record.VoiceRecorder;

import java.io.File;

/**
 * Created by Sea on 12/12/2015.
 */
public class RecordDialog {
    private MaterialDialog mDialog;
    private VoiceRecorder voiceRecorder;
    private OnFinishListener mCallback;


    public interface OnFinishListener {
        void onFinish();
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        mCallback = onFinishListener;
    }

    public RecordDialog(final Context context) {
        mCallback = null;
        voiceRecorder = new VoiceRecorder();
        mDialog = new MaterialDialog.Builder(context)
                .title("录音")
                .titleColor(context.getResources().getColor(R.color.skyblue))
                .customView(R.layout.dialog_record, false)
                .positiveText("确定")
                .positiveColor(context.getResources().getColor(R.color.skyblue))
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        voiceRecorder.start();
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        voiceRecorder.stop();
                        if (mCallback != null)
                            mCallback.onFinish();
                    }
                })
                .show();

    }
}
