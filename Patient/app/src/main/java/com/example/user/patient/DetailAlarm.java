package com.example.user.patient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 3/14/2016.
 */
public class DetailAlarm extends Activity {

    TextView nameDrug;
    TextView amount;
    TextView be_af;
    TextView hr;
    TextView mn;
    String id_assign;
    String hr_s;
    String mn_s;
    String id_p;
    RadioButton stat;
    Button senddata;
    Button back;
    Button detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailalarm);
        Intent i=getIntent();
        id_assign=i.getStringExtra("ID_assign");
        hr_s=i.getStringExtra("hour");
        mn_s=i.getStringExtra("minute");
        id_p=i.getStringExtra("patient_id");
        showdata();
        back=(Button)findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showdata(){
        final String url="http://104.131.23.206/patient/showdata_alarm2.php?assign_id="+id_assign;
        nameDrug=(TextView)findViewById(R.id.namedrug);
        amount=(TextView)findViewById(R.id.amount);
        be_af=(TextView)findViewById(R.id.before_after);
        hr=(TextView)findViewById(R.id.hour);
        mn=(TextView)findViewById(R.id.minute);

         class LoaddetailTask extends AsyncTask<String,Void,String>{
             Context mContext;
             public LoaddetailTask(Context context){
                 mContext=context;

             }

            @Override
            protected String doInBackground(String... params) {
                String jsonString=JsonHttp.makeHttpRequest(params[0]);
                //Log.d("rr", "bb" + jsonString);
                return jsonString;
            }

             @Override
             protected void onPostExecute(String s) {
                 try {
                     JSONObject json=new JSONObject(s);
                     final String id_p=json.getString("patient_id");
                     //Log.d("aa","bb"+id_p);
                     final String drug_id=json.getString("drug_id");
                     String drug_name=json.getString("Drug_name");
                     String amount_s=json.getString("amount");
                     String before_after_s=json.getString("before_after");
                     Calendar c = Calendar.getInstance();
                     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                     String formattedDate = df.format(c.getTime());

                     SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
                     String time = df2.format(Calendar.getInstance().getTime());

                     stat = (RadioButton) findViewById (R.id.radioButton);

                     String a;
                     if(stat.isChecked()) {
                         a = "eat";
                     } else a ="Don't eat";

                     String sts =a.replace(" ","%20");

                     nameDrug.setText(drug_name);
                     amount.setText(amount_s);
                     be_af.setText(before_after_s);
                     hr.setText(hr_s);
                     mn.setText(mn_s);

                     final String url_h="http://104.131.23.206/patient" +
                             "/history.php?patient_id="+id_p+"&drug_id="+drug_id+"&date="+formattedDate+"&status="+sts+"&Time1="+time;

                     senddata=(Button) findViewById(R.id.btn_h);
                     senddata.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                            // Log.d("tt","ll"+url_h);
                             HistoryTask task=new HistoryTask(mContext);
                             task.execute(url_h);


                             Toast.makeText(mContext, "Send data success", Toast.LENGTH_SHORT).show();
                         }
                     });
                     detail=(Button)findViewById(R.id.detail);
                     detail.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Intent ii=new Intent(DetailAlarm.this,Infodrug.class);
                             ii.putExtra("ID_drug",drug_id);
                             startActivity(ii);
                         }
                     });


                 }catch (JSONException e){
                     e.printStackTrace();
                 }
             }
         }

        LoaddetailTask task=new LoaddetailTask(this);
        task.execute(url);



    }
}
