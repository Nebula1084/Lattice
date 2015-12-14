package com.sea.lattice.ui.record;

import com.czt.mp3recorder.MP3Recorder;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sea on 12/8/2015.
 */
public class VoiceRecorder {

    public final static File TMP_FILE;
    private MP3Recorder mp3Recorder;

    static {
        String path = "/sdcard/lattice/audio";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        TMP_FILE = new File(path, "tmp_audio.mp3");
    }

    public VoiceRecorder() {

        mp3Recorder=new MP3Recorder(TMP_FILE);
    }

    public boolean start() {
        try {
            mp3Recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean stop() {
        mp3Recorder.stop();
        return true;
    }
}
