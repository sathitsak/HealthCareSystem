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
 * Created by user on 3/3/2016.
 */
public class Addassign extends Activity {

    Button assign;
    Button cancel;
    Button takepic;
    String id_c;
    String id_p;
    EditText namedrug;
    EditText amount;
    EditText h1,h2,h3,h4,m1,m2,m3,m4;
    RadioButton before;
    String ms="";
    String drugname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addassign);

        Intent i=getIntent();
        id_c=i.getStringExtra("caretaker_id");
        id_p=i.getStringExtra("patient_id");
        drugname=i.getStringExtra("drugname");
        namedrug=(EditText)findViewById(R.id.namedrug_a);
        amount=(EditText)findViewById(R.id.amount_a);
        before = (RadioButton) findViewById (R.id.radioButton);
        takepic=(Button)findViewById(R.id.takePic);

        h1=(EditText)findViewById(R.id.ahour1);
        h2=(EditText)findViewById(R.id.ahour2);
        h3=(EditText)findViewById(R.id.ahour3);
        h4=(EditText)findViewById(R.id.ahour4);

        m1=(EditText)findViewById(R.id.aminute1);
        m2=(EditText)findViewById(R.id.aminute2);
        m3=(EditText)findViewById(R.id.aminute3);
        m4=(EditText)findViewById(R.id.aminute4);

        cancel=(Button)findViewById(R.id.btn_cancel);
        assign=(Button)findViewById(R.id.btn_a);
        namedrug.setText(drugname);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Addassign.this,Assign.class);
                ii.putExtra("patient_id",id_p);
                ii.putExtra("caretaker_id",id_c);
                startActivity(ii);
            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adddata()){
                    finish();
                }
            }
        });

        takepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Addassign.this,UploadMainActivity.class);
                ii.putExtra("patient_id",id_p);
                ii.putExtra("caretaker_id",id_c);
                startActivity(ii);
            }
        });


    }
    private boolean adddata(){
        SendNotiTask task2=new SendNotiTask(id_p);
        task2.execute();
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        String url="http://104.131.23.206/caretaker/assign.php?";
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


        url+="patient_id="+id_p;
        url+="&caretaker_id="+id_c;

        if(namedrug.getText().length() == 0) //Drug_name
        {
            ad.setMessage("กรุณาใส่ชื่อยา ");
            ad.show();
            namedrug.requestFocus();
            return false;
        }

        String str =namedrug_s.replace(" ","%20");
        url+="&drug_name="+str;

        if(amount.getText().length() == 0) // จำนวนยา
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
            else if(h1_int<5||h1_int>10){
                ad.setMessage("กรุณาใส่ช่วงเวลาให้อยู่ในช่วง5.00-10.00");
                ad.show();
                return false;
            }
        }

        if(h22.length()==0){
            h22="0";
        }else {
            int h2_int = Integer.parseInt(h22);
            if(h2_int == 0) {}
            else if(h2_int<11||h2_int>14){
                ad.setMessage("กรุณาใส่ช่วงเวลาให้อยู่ในช่วง11.00-14.00");
                ad.show();
                return false;
            }
        }

        if(h33.length()==0){
            h33="0";
        }else {
            int h3_int = Integer.parseInt(h33);
            if(h3_int == 0) {}
            else if(h3_int<15||h3_int>19){
                ad.setMessage("กรุณาใส่ช่วงเวลาให้อยู่ในช่วง15.00-19.00");
                ad.show();
                return false;
            }
        }

        if(h44.length()==0){
            h44="0";
        }else {
            int h4_int = Integer.parseInt(h44);
            if(h4_int == 0) {}
            else if(h4_int<20||h4_int>23){
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


        //Log.d("qq",""+url);
        /////////////////////////////////////// Asyn

        class AddassignTask extends AsyncTask<String,Void,String>{
            Context aContext;
            String sts;
            String message;

            public AddassignTask (Context context){
                aContext=context;

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
                    Toast.makeText(aContext, "เพิ่มการแจ้งเตือนสำเร็จ", Toast.LENGTH_LONG).show();}
                else{
                    Toast.makeText(aContext, message, Toast.LENGTH_LONG).show();}

            }


        }


        AddassignTask task=new AddassignTask(this);
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
