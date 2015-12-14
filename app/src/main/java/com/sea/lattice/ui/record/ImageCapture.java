package com.sea.lattice.ui.record;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Sea on 12/12/2015.
 */
public class ImageCapture {
    private Context context;
    private Uri photoUri;
    static private final String TEMP_PHOTO = "lattic_temp_photo";
    static public final int CAPUTRE_REQ = 10;
    static public final File TMP_FILE;

    static {
        String path = "/sdcard/lattice/photo";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        TMP_FILE = new File(path, "tmp_photo.jpg");
    }

    public ImageCapture(Context context) {
        this.context = context;
    }

    public void capture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Video.Media.TITLE, TEMP_PHOTO);

        photoUri = context.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        Activity activity = (Activity) context;
        activity.startActivityForResult(intent, CAPUTRE_REQ);
    }

    public Bitmap getImage() throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), photoUri);
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 0.1f;
        float scaleHeight = 0.1f;
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, new FileOutputStream(TMP_FILE));
        return Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true);
    }

    public String getPath(){
        return photoUri.getPath();
    }
}
