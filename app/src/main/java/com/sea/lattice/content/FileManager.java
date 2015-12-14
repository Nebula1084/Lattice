package com.sea.lattice.content;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sea on 12/13/2015.
 */
public class FileManager {
    private Context context;

    public FileManager(Context context) {
        this.context = context;
    }

    public static void copy(String src, String dst) throws IOException {
        if (src.equals(dst))
            return;
        File srcFile = new File(src);
        File dstFile = new File(dst);
        if (!srcFile.exists())
            throw new FileNotFoundException(src);
        copy(srcFile, dstFile);
    }

    public static void copy(File src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();

        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    public static String newAudio(String src) throws IOException {
        if (src == null)
            return null;
        String path = Environment.getExternalStorageDirectory() + "/lattice/audio";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        File dst = new File(dir, Calendar.getInstance().getTimeInMillis() + ".mp3");
        copy(new File(src), dst);
        return dst.getPath();
    }

    public static String newPhoto(String src) throws IOException {
        if (src == null)
            return null;
        String path = Environment.getExternalStorageDirectory() + "/lattice/photo";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        File dst = new File(dir, Calendar.getInstance().getTimeInMillis() + ".jpg");
        copy(new File(src), dst);
        return dst.getPath();
    }

}
