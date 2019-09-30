package com.example.user.patient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user on 3/11/2016.
 */
public class NotifyService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent ww)
    {
       //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, Alarmon.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtras(ww);
        context.startActivity(i);
       /*  Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
*/
    }
}
