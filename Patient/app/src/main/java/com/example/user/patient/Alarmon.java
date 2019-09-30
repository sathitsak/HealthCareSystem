package com.example.user.patient;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 3/11/2016.
 */
public class Alarmon extends Activity implements AdapterView.OnItemClickListener{
    ListView list;
    ArrayList<HashMap<String, String>> MyArrList;
    String id_p;
    String hr;
    String mn;
    Button back;
    Button stop;
    Ringtone ringtone;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_alarm);
        long[] pattern = {0, 1000, 1000};

        sharedPref=getPreferences(Context.MODE_PRIVATE);
        editor=sharedPref.edit();
        int c=sharedPref.getInt("b", 0);

        if(c==1) {
            PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
            wakeLock.acquire();

            KeyguardManager keyguardManager = (KeyguardManager) getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("TAG");
            keyguardLock.disableKeyguard();

            Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);//song
            ringtone = RingtoneManager.getRingtone(this, notif);
            ringtone.play();

            vibrator = (Vibrator) this                                   //สั้น
                    .getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern, 0);
        }


            Intent i = getIntent();
            id_p = i.getStringExtra("patient_id");
            hr = i.getStringExtra("hour");
            mn = i.getStringExtra("minute");

            list = (ListView) findViewById(R.id.listView);
            list.setOnItemClickListener(this);
            back = (Button) findViewById(R.id.btn_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putInt("b", 1);
                    editor.commit();


                    finish();
                }
            });
        stop=(Button)findViewById(R.id.btn_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("b", 2);
                editor.commit();
                vibrator.cancel();


                ringtone.stop();
            }
        });
            showdata();


    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }

    public void showdata(){

        String url="http://104.131.23.206/patient/showdata_alarm.php?user_id="+id_p+"&hr="+hr+"&mn="+mn;

        ShowlistAlarmTask task=new ShowlistAlarmTask(this);
        task.execute(url);
    }
    @Override
    public void onItemClick(AdapterView<?> parent,View v,int position,long id){
        String rowId=((TextView)v.findViewById(R.id.idassign)).getText().toString();
        Intent newActivity = new Intent(this,DetailAlarm.class);
        //Log.d("aa","bb"+hr);
        newActivity.putExtra("ID_assign",rowId);
        newActivity.putExtra("patient_id",id_p);
        newActivity.putExtra("hour",hr);
        newActivity.putExtra("minute",mn);
        startActivity(newActivity);

    }





    //////////////////////////////////////////////////////////////

}
