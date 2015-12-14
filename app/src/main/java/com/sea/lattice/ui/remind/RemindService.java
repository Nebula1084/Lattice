package com.sea.lattice.ui.remind;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.util.Calendar;

/**
 * Created by Sea on 10/21/2015.
 */
public class RemindService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public static void activate(Context context, Calendar calendar, Bundle bundle){
        Intent intent = new Intent(context, RemindService.class);

    }
}
