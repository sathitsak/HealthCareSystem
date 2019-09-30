package com.example.user.caretaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by user on 2/15/2016.
 */
public class Menu_patient extends Activity {

    Button chksts;
    Button assign;
    Button camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menupatient);

        Intent yy = getIntent();
        final String rr = yy.getStringExtra("patient_id");
        //final String tt = yy.getStringExtra("username");
        final String gg = yy.getStringExtra("caretaker_id");

        chksts=(Button)findViewById(R.id.check_status);
        assign=(Button)findViewById(R.id.assign);
        camera=(Button)findViewById(R.id.camera);

        chksts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yy = new Intent(Menu_patient.this,CheckStatus.class);
                yy.putExtra("patient_id", rr);
                yy.putExtra("caretaker_id", gg);
                //yy.putExtra("username", tt);
                startActivity(yy);
            }
        });


        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yy = new Intent(Menu_patient.this,Assign.class);
                yy.putExtra("patient_id", rr);
                yy.putExtra("caretaker_id", gg);
                //yy.putExtra("username", tt);
                startActivity(yy);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu_patient.this, " Webcam has been select ", Toast.LENGTH_SHORT).show();
                Intent LaunchIntent=getPackageManager().getLaunchIntentForPackage("com.teamviewer.teamviewer.market.mobile");
                startActivity(LaunchIntent);
            }
        });


    }
}
