package com.example.user.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by user on 4/9/2016.
 */
public class CheckHisExcer extends Activity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkhisexcer);
        Intent yy = getIntent();
        list=(ListView)findViewById(R.id.listView2);
        final String rr = yy.getStringExtra("patient_id");
        //Log.d("aa","bb"+rr);
        //final String tt = yy.getStringExtra("username");

        //StatusTask task=new StatusTask(this,rr);
        //task.execute();

        CheckHisExcerTask task=new CheckHisExcerTask(this,rr);
        task.execute();



    }
}
