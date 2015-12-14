package com.sea.lattice.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.dexafree.materialList.model.CardItemView;
import com.sea.lattice.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sea on 12/12/2015.
 */
public class LatticeCardItemView extends CardItemView<LatticeCard> {

    public LatticeCardItemView(Context context) {
        super(context);
    }

    public LatticeCardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LatticeCardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void build(LatticeCard card) {
        setDate(card.getDate());
        setBehavior(card.getBehavior());
        setContent(card.getContent());
        setAudio(card.getAudio());
        setPhoto(card.getPhoto());
    }

    public void setDate(Date date) {
        TextView card_date = (TextView) findViewById(R.id.card_date);
        Format format = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
        card_date.setText(format.format(date));
    }

    public void setBehavior(String behavior) {
        TextView card_behave = (TextView) findViewById(R.id.card_behave);
        card_behave.setText(behavior);
    }

    public void setContent(String content) {
        TextView card_description = (TextView) findViewById(R.id.card_description);
        card_description.setText(content);
    }

    public void setPhoto(String src) {
        if (src == null || src.equals("")) return;
        try {
            ImageView card_photo = (ImageView) findViewById(R.id.card_photo);
            File file = new File(src);
            boolean b = file.exists();
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            float width = bitmap.getWidth();
            float height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = 0.1f;
            float scaleHeight = 0.1f;
            matrix.postScale(scaleWidth, scaleHeight);

            card_photo.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setAudio(String src) {
        if (src == null || src.equals("")) return;
        LatticePlayer card_player = (LatticePlayer) findViewById(R.id.card_player);
    }
}
