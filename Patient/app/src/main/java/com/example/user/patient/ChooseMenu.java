package com.example.user.patient;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by user on 1/27/2016.
 */
public class ChooseMenu extends Activity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private boolean isReceiverRegistered;

    String id_p;
    String tk;
    Button fall_warning;
    Button exercise;
    Button show_sts;
    ArrayList<HashMap<String, String>> MyArrList;
    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosemenu);
        Intent i=getIntent();
        id_p=i.getStringExtra("patient_id");

        SharedPreferences sharedPref=PreferenceManager.getDefaultSharedPreferences(this);
        tk=sharedPref.getString("token","no");
        //Log.d("qq", "rr" + tk);
/*
        TextView amountAssign=(TextView)findViewById(R.id.amount_as);
        String b=amountAssign.getText().toString();*/

        //amount_list=Integer.parseInt(b);
        //Log.d("aaa", "bb----->" +  b);
        registerGcm();


        ///////////////////////////////////////////////////////
        if(MyService.check_fall_exercise == true && AccelerometerMenuActivity.check_exercise_main == true)
        {
            startService(new Intent(ChooseMenu.this, MyService.class));

            MyService.check_fall_exercise = false;
            AccelerometerMenuActivity.check_exercise_main = false;

        }
        fall_warning=(Button)findViewById(R.id.fallwarning);
        exercise=(Button)findViewById(R.id.exercise);
        show_sts=(Button)findViewById(R.id.checkstatus);

        fall_warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMenu.this, " Fall Warning has been select ", Toast.LENGTH_SHORT).show();
                Intent it = new Intent (getApplicationContext(),FallWarningActivity.class);
                startActivity(it);

            }
        });

        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMenu.this, " Exercise Count has been select ", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(getApplicationContext(), AccelerometerMenuActivity.class);
                it.putExtra("patient_id",id_p);
                startActivity(it);
            }
        });

        show_sts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseMenu.this, " Check Status has been select ", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(getApplicationContext(), CheckStatus.class);
                it.putExtra("patient_id",id_p);
                startActivity(it);

            }
        });


    }




    private void registerGcm() {
        Intent intent = new Intent(this, GcmRegisterService.class);
        intent.putExtra("patient_id", id_p);
        startService(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        ShowData();
    }


    public void ShowData() // method ShowData
    {
        String url="http://104.131.23.206/patient/showall_assign.php?user_id="+id_p;

        class ShowDataTask extends AsyncTask<String,Void,String> {
            private Context  mContext;
            private final ChooseMenu reference;
            String id_p;

            public ShowDataTask(ChooseMenu reference,String p){
                this.reference = reference;
                id_p=p;
            }

            @Override
            protected String doInBackground(String... params) {
                String jsonString=JsonHttp.makeHttpRequest(params[0]);
                //   Log.d("aa", "bb"+jsonString);
                return jsonString;
            }

            @Override
            protected void onPostExecute(String jsonString) {
                int l=0;
                TextView amountAssign=(TextView)findViewById(R.id.amount_as);
                ListView list=(ListView)findViewById(R.id.listViewassign);
                try {
                    JSONArray data = new JSONArray(jsonString);
                    MyArrList = new ArrayList<HashMap<String, String>>();
                    HashMap<String, String> map;

                    for(int i = 0; i < data.length(); i++) {

                        JSONObject c = data.getJSONObject(i);
                        map = new HashMap<String, String>();

                        map.put("Assign_id", c.getString("Assign_id"));
                        map.put("amount", c.getString("amount"));
                        map.put("Drug_name", c.getString("Drug_name"));
                        //map.put("time", c.getString("time"));
                        map.put("before_after", c.getString("before_after"));
                        // map.put("Picture_url", c.getString("Picture_url"));
                        map.put("hour1", c.getString("hour1"));
                        map.put("minute1", c.getString("minute1"));
                        map.put("hour2", c.getString("hour2"));
                        map.put("minute2", c.getString("minute2"));
                        map.put("hour3", c.getString("hour3"));
                        map.put("minute3", c.getString("minute3"));
                        map.put("hour4", c.getString("hour4"));
                        map.put("minute4", c.getString("minute4"));
                        MyArrList.add(map);

                        String[] keys=new String[]{"Assign_id","amount","Drug_name",
                                "before_after","hour1","minute1","hour2","minute2"
                                ,"hour3","minute3","hour4","minute4"
                        };

                        int[] views=new int[]{
                                R.id.assignID,
                                R.id.amount,
                                R.id.drugname,
                                R.id.before_after,
                                R.id.hour1,R.id.minute1,
                                R.id.hour2,R.id.minute2,
                                R.id.hour3,R.id.minute3,
                                R.id.hour4,R.id.minute4
                        };
                        ListAdapter adapter=new SimpleAdapter(ChooseMenu.this,MyArrList,R.layout.item_alarm,keys,views);
                        list.setAdapter(adapter);
                        l=list.getAdapter().getCount();
                        String a=""+l;
                        amountAssign.setText(a);

                    }
                    setalarm(l,MyArrList);




                }catch (JSONException e){

                    e.printStackTrace();
                }

            }
        }
        ShowDataTask task=new ShowDataTask(this,id_p);
        task.execute(url);
    }

    public void setalarm(int amount,ArrayList<HashMap<String, String>> MyArrList) {
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        clearpending();

        // Log.d("ee","xx"+MyArrList);
         //Log.d("ee","xx"+MyArrList.get(0).get("hour1"));
        PendingIntent pendingIntent;

        for(int position=0;position<amount;position++){

            int[] hr = {Integer.parseInt(MyArrList.get(position).get("hour1")),
                    Integer.parseInt(MyArrList.get(position).get("hour2")),
                    Integer.parseInt(MyArrList.get(position).get("hour3")),
                    Integer.parseInt(MyArrList.get(position).get("hour4"))};


            int[] mn = {Integer.parseInt(MyArrList.get(position).get("minute1")),
                    Integer.parseInt(MyArrList.get(position).get("minute2")),
                    Integer.parseInt(MyArrList.get(position).get("minute3")),
                    Integer.parseInt(MyArrList.get(position).get("minute4"))};



            for(int j=0; j<hr.length; j++) {
                if(hr[j]!=0) {
                    Intent ww = new Intent(getBaseContext(), NotifyService.class);
                    ww.putExtra("patient_id", id_p);
                    ww.putExtra("hour", String.valueOf(hr[j]));
                    ww.putExtra("minute", String.valueOf(mn[j]));

                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    final int _id = (int) System.currentTimeMillis();
                    // Log.d("ab","ba"+_id);
                    String pe_id = "" + _id;
                    savependingid(pe_id);
                    pendingIntent = PendingIntent.getBroadcast(this, _id, ww, PendingIntent.FLAG_CANCEL_CURRENT);

                    Calendar now = Calendar.getInstance();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    // ตั้งเวลา แอปแจ้งเตือนตาม ชั่วโมง hr[j]
                    calendar.set(Calendar.HOUR_OF_DAY, hr[j]);
                    // ตั้งเวลา แอปแจ้งเตือนตาม นาที mn[j]
                    calendar.set(Calendar.MINUTE, mn[j]);
                    calendar.set(Calendar.SECOND, 0);

                    long _alarm = 0; //เช็คเวลาแจ้งเตือน และเวลาปัจจุบัน  ถ้าเวลาแจ้งเตือนน้อยกว่าเวลาปัจจุบัน จะ
                    if (calendar.getTimeInMillis() <= now.getTimeInMillis())
                        _alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1);
                    else
                        _alarm = calendar.getTimeInMillis();

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm,
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                }

            }

        }

/*
        Intent aa=new Intent(this,NotifyService.class);
            aa.putExtra("patient_id", id_p);
            aa.putExtra("hour","18");
            aa.putExtra("minute", "18");
        PendingIntent p0;

        p0=PendingIntent.getBroadcast(this,0,aa,0);
        AlarmManager a=(AlarmManager)getSystemService(ALARM_SERVICE);
        a.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 2000, p0);
        */

    }

    public void savependingid(String id){
        //Log.d("ab","ba"+id);
        String url="http://104.131.23.206/patient/save_id_pen.php?pen_id="+id;
        SavependingTask task=new SavependingTask();
        task.execute(url);
    }

    public void clearpending(){
        ClearPendingTask task=new ClearPendingTask(this);
        task.execute();


    }

}
