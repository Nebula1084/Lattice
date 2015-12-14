package com.sea.lattice.widget;

import android.content.Context;

import com.dexafree.materialList.cards.SimpleCard;
import com.sea.lattice.R;

import java.util.Date;

/**
 * Created by Sea on 12/12/2015.
 */
public class LatticeCard extends SimpleCard {
    private Date date;
    private String behavior;
    private String content;
    private String audio_src;
    private String photo_src;

    public LatticeCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.lattice_card_item;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAudio(String src) {
        this.audio_src = src;
    }

    public void setPhoto(String src) {
        this.photo_src = src;
    }

    public Date getDate(){
        return  date;
    }

    public String getBehavior(){
        return behavior;
    }

    public String getContent(){
        return content;
    }

    public String getAudio(){
        return audio_src;
    }

    public String getPhoto(){
        return photo_src;
    }
}
