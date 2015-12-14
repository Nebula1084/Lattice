package com.sea.lattice.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.sea.lattice.R;
import com.sea.lattice.ui.record.VoicePlayer;
import com.sea.lattice.ui.record.VoiceRecorder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sea on 12/13/2015.
 */
public class LatticePlayer extends LinearLayout implements OnClickListener {
    private final String TAG = VoicePlayer.class.getName();

    private MaterialPlayPauseButton play_pause;
    private ProgressBarDeterminate player_progressDeterminate;

    private MediaPlayer mPlayer;
    private Timer timer;
    private TimerTask timerTask;
    private Handler mHandler;

    private String path;

    public LatticePlayer(Context context) {
        super(context);
        init();
    }

    public LatticePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatticePlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.lattice_player, this);
        play_pause = (MaterialPlayPauseButton) findViewById(R.id.player_pause);
        player_progressDeterminate = (ProgressBarDeterminate) findViewById(R.id.player_progressDeterminate);
        play_pause.setOnClickListener(this);
        play_pause.setAnimDuration(300);
        mPlayer = new MediaPlayer();
        path = VoiceRecorder.TMP_FILE.getPath();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player_pause:
                switch (play_pause.getState()) {
                    case MaterialPlayPauseButton.PLAY:
                        play_pause.setToPause();
                        start();

                        break;
                    case MaterialPlayPauseButton.PAUSE:
                        play_pause.setToPlay();
                        stop();
                        break;
                }
                break;
        }
    }

    public void setSource(String path) {
        this.path = path;
    }

    public boolean start() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.getCurrentPosition();
            setProgress();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stop();
                    play_pause.setToPlay();
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
        timer.cancel();
        mHandler = null;
        player_progressDeterminate.setProgress(0);
        return true;
    }

    private void setProgress() {
        player_progressDeterminate.setMin(0);
        player_progressDeterminate.setMax(mPlayer.getDuration());
        timer = new Timer();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (mPlayer != null)
                    player_progressDeterminate.setProgress(mPlayer.getCurrentPosition());
            }
        };
        timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask, 0, 10);

    }
}

