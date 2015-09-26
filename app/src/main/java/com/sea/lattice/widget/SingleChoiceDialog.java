package com.sea.lattice.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;

import com.sea.lattice.R;

/**
 * Created by Sea on 9/26/2015.
 */
public class SingleChoiceDialog extends AlertDialog {
    private int selected;

    protected SingleChoiceDialog(Context context, String[] options) {
        this(context, 0, options);
    }

    static int resovleDialogTheme(Context context, int resid) {
        if (resid == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.alertDialogTheme, outValue, true);
            return outValue.resourceId;
        } else {
            return resid;
        }
    }

    protected SingleChoiceDialog(Context context, int theme, String[] options) {
        super(context, resovleDialogTheme(context, theme));
        selected = 0;
        
    }

    public int getSelected(){
        return selected;
    }

}
