package com.example.user.caretaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by user on 3/1/2016.
 */
public class CheckStatus extends Activity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkstatus);
        Intent yy = getIntent();
        list=(ListView)findViewById(R.id.listView);
        final String rr = yy.getStringExtra("patient_id");
        //final String tt = yy.getStringExtra("username");
        final String gg = yy.getStringExtra("caretaker_id");

        StatusTask task=new StatusTask(this,rr);
        task.execute();

    }
}
