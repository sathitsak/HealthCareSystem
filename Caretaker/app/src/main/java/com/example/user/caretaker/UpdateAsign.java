package com.example.user.caretaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 3/6/2016.
 */
public class UpdateAsign extends Activity {

    Button update;
    Button cancel;
    String id_p;
    String id_c;
    String drugname;
    EditText namedrug;
    String id_a;
    EditText amount;
    EditText h1,h2,h3,h4,m1,m2,m3,m4;
    RadioButton before;
    String ms="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateassign);

        Intent a=getIntent();
        id_p=a.getStringExtra("patient_id");
        id_c=a.getStringExtra("caretaker_id");
        drugname=a.getStringExtra("Drug_name");
        id_a=a.getStringExtra("ID");

        update=(Button)findViewById(R.id.btn_u);
        cancel=(Button)findViewById(R.id.btn_cancel);
        amount=(EditText)findViewById(R.id.amount_u);
        before = (RadioButton) findViewById (R.id.radioButton);
        namedrug=(EditText)findViewById(R.id.namedrug_u);
        namedrug.setText(drugname);

        h1=(EditText)findViewById(R.id.uhour1);
        h2=(EditText)findViewById(R.id.uhour2);
        h3=(EditText)findViewById(R.id.uhour3);
        h4=(EditText)findViewById(R.id.uhour4);

        m1=(EditText)findViewById(R.id.uminute1);
        m2=(EditText)findViewById(R.id.uminute2);
        m3=(EditText)findViewById(R.id.uminute3);
        m4=(EditText)findViewById(R.id.uminute4);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(update()){
                    finish();
                }
            }
        });
    }

    private boolean update(){


        SendNotiTask task2=new SendNotiTask(id_p);
        task2.execute();
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        String url="http://104.131.23.206/caretaker/updateassign.php?";
        String a;

        String h11=h1.getText().toString().trim();
        String h22=h2.getText().toString().trim();
        String h33=h3.getText().toString().trim();
        String h44=h4.getText().toString().trim();

        String m11=m1.getText().toString().trim();
        String m22=m2.getText().toString().trim();
        String m33=m3.getText().toString().trim();
        String m44=m4.getText().toString().trim();

        String amount_s=amount.getText().toString().trim();
        String namedrug_s=namedrug.getText().toString().trim();

        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);


        url+="id="+id_a+"&patient_id="+id_p+"&caretaker_id="+id_c;

        if(namedrug.getText().length() == 0) //Drug_name
        {
            ad.setMessage("กรุณาใส่ชื่อยา");
            ad.show();
            namedrug.requestFocus();
            return false;
        }
        String str =namedrug_s.replace(" ","%20");
        url+="&drug_name="+str;

        if(amount.getText().length() == 0) // �ӹǹ��
        {
            ad.setMessage("กรุณาใส่จำนวนยา");
            ad.show();
            amount.requestFocus();
            return false;
        }
        url+="&amount="+amount_s;

        if(before.isChecked()) {
            a = "before";
        } else a ="after";

        url+="&before_after="+a;

        if(h11.length()==0){
            h11="0";
        }else {
            int h1_int = Integer.parseInt(h11);
            if(h1_int == 0) {}
            else if (h1_int < 5 || h1_int > 10) {
                ad.setMessage("กรุณาใส่ช่วงเวลาให้อยู่ในช่วง5.00-10.00");
                ad.show();
                return false;
                }

        }

        if(h22.length()==0){
            h22="0";
        }else {
            int h2_int = Integer.parseInt(h22);
           if (h2_int==0){}
            else if (h2_int < 11 || h2_int > 14) {
               ad.setMessage("กรุณาใส่ช่วงเวลาให้อยู่ในช่วง11.00-14.00");
               ad.show();
               return false;
            }
        }

        if(h33.length()==0){
            h33="0";
        }else {
            int h3_int = Integer.parseInt(h33);
            if (h3_int==0){}
            else if(h3_int<15||h3_int>19||h3_int==0){
                ad.setMessage("กรุณาใส่ช่วงเวลาให้อยู่ในช่วง15.00-19.00");
                ad.show();
                return false;
            }
        }

        if(h44.length()==0){
            h44="0";
        }else {
            int h4_int = Integer.parseInt(h44);
            if (h4_int==0){}
            else if(h4_int<20||h4_int>23||h4_int==0){
                ad.setMessage("กรุณาใส่ช่วงเวลาให้อยู่ในช่วง20.00-23.00");
                ad.show();
                return false;
            }
        }

        url+="&h1="+h11+"&h2="+h22+"&h3="+h33+"&h4="+h44;

        if (m11.length()==0){
            m11="0";
        }else {
            if(checkminute(m11)){
                ad.setMessage("กรุณาใส่ช่วงนาทีให้ถูกต้อง");
                ad.show();
                return false;
            }
        }

        if (m22.length()==0){
            m22="0";
        }else {
            if(checkminute(m22)){
                ad.setMessage("กรุณาใส่ช่วงนาทีให้ถูกต้อง");
                ad.show();
                return false;
            }
        }

        if (m33.length()==0){
            m33="0";
        }else {
            if(checkminute(m33)){
                ad.setMessage("กรุณาใส่ช่วงนาทีให้ถูกต้อง");
                ad.show();
                return false;
            }
        }

        if (m44.length()==0){
            m44="0";
        }else {
            if(checkminute(m44)){
                ad.setMessage("กรุณาใส่ช่วงนาทีให้ถูกต้อง");
                ad.show();
                return false;
            }
        }


        url+="&m1="+m11+"&m2="+m22+"&m3="+m33+"&m4="+m44;
        Log.d("qq",""+url);
        /////////////////////////////////////// Asyn

        class UpdateTask extends AsyncTask<String,Void,String>{
            Context uContext;
            String sts;
            String message;

            public UpdateTask (Context context){
                uContext=context;

            }

            @Override
            protected String doInBackground(String... params) {
                // Log.d("aa", "bb"+"pass");
                String jsonString=JsonHttp.makeHttpRequest(params[0]);
                //  Log.d("aa", "bb" + jsonString);
                return jsonString;
            }

            @Override
            protected void onPostExecute(String jsonString){
                try {
                    JSONObject c = new JSONObject(jsonString);
                    // Log.d("aa", "bb" + c);
                    sts=c.getString("StatusID");
                    message=c.getString("Error");
                    ms=sts;
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if(sts.equals("1")){
                    Toast.makeText(uContext, "อัพเดตการแจ้งเตือนสำเร็จ", Toast.LENGTH_LONG).show();}
                else{
                    Toast.makeText(uContext, message, Toast.LENGTH_LONG).show();}

            }
        }


        UpdateTask task=new UpdateTask(this);
        //Log.d("aa", "bb" + ms);
        task.execute(url);

        return false;

    }

    private boolean checkminute(String a){
        int minute = Integer.parseInt(a);

        for(int i=0;i<=60;i++){
            if(minute==i){
                return false;
            }
        }
        return true;
    }


}
