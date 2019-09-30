package com.example.user.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by user on 1/28/2016.
 */
public class AccelerometerMenuActivity extends Activity{
    public static boolean check_exercise_main;
    String id_p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometermenupage);

        final Intent i=getIntent();
        id_p=i.getStringExtra("patient_id");

        check_exercise_main = true;

        ImageButton HorizontalButton = (ImageButton) findViewById(R.id.HorizontalButton);
        ImageButton VerticalButton = (ImageButton) findViewById(R.id.VerticalButton);

        if(MyService.check_fall_exercise == true)
        {
            stopService(new Intent(AccelerometerMenuActivity.this, MyService.class));
        }

        ImageButton b = (ImageButton) findViewById(R.id.buttonBackToMain);
        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent it = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(it);
                finish();
            }
        });

        Button ex_button=(Button)findViewById(R.id.exBT);
        ex_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent (getApplicationContext(),CheckHisExcer.class);
                it.putExtra("patient_id",id_p);
                startActivity(it);
            }
        });

        HorizontalButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(AccelerometerMenuActivity.this, " Vertical Exercise has been select ", Toast.LENGTH_SHORT).show();
                Intent it = new Intent (getApplicationContext(),AccelerometerActivity.class);
                it.putExtra("patient_id",id_p);
                startActivity(it);
                finish();
            }
        });

        VerticalButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(AccelerometerMenuActivity.this, " Horizontal Exercise has been select ", Toast.LENGTH_SHORT).show();
                Intent it = new Intent (getApplicationContext(),Accelerometer2Activity.class);
                it.putExtra("patient_id",id_p);
                startActivity(it);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }
}
