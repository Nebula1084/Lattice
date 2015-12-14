package com.sea.lattice.ui.record;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by Sea on 12/8/2015.
 */
public class VoicePlayer {
    private final String TAG = VoicePlayer.class.getName();

    private MediaPlayer mPlayer;

    public VoicePlayer() {
    }

    public boolean start() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(VoiceRecorder.TMP_FILE.getPath());
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "prepare() failed");
            return false;
        }

        return true;
    }

    public boolean stop() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        return true;
    }
}
