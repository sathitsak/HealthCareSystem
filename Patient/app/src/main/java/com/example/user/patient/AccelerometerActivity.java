package com.example.user.patient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 1/28/2016.
 */
public class AccelerometerActivity extends Activity{
    String id_p;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    // Horizontal Line
    TextView textX, textY, textZ,countExTV;
    SensorManager sensorManager;
    Sensor sensor;
    float mLastX, mLastY, mLastZ;
    //static float NOISE = (float) 16.65;
    boolean check = false;
    int checkcount = 0;
    int i,count = 0;
    float deltaX,deltaY,deltaZ = 0;
    float x,x1,x2;
    float y,y1,y2;
    float z,z1,z2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometerpage);

        Intent i=getIntent();
        id_p=i.getStringExtra("patient_id");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        final EditText noiseEditText = (EditText) findViewById(R.id.noiseET);

        textX = (TextView) findViewById(R.id.textX);
        textY = (TextView) findViewById(R.id.textY);
        textZ = (TextView) findViewById(R.id.textZ);

        textX.setVisibility(View.INVISIBLE);
        textY.setVisibility(View.INVISIBLE);
        textZ.setVisibility(View.INVISIBLE);

        countExTV = (TextView) findViewById(R.id.countExTV);

        ImageButton add_h=(ImageButton)findViewById(R.id.addtohis);
        add_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://104.131.23.206/patient/history_excer.php?";

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());

                SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
                String time = df2.format(Calendar.getInstance().getTime());
                //Log.d("aa","bb"+id_p);
                url+="patient_id="+id_p+"&count="+count+"&date="+formattedDate+"&Time="+time;
                AddHisExcerTask task=new AddHisExcerTask();
                task.execute(url);

                Toast.makeText(AccelerometerActivity.this, "Send data success", Toast.LENGTH_SHORT).show();

            }
        });


        ImageButton ResetBT = (ImageButton) findViewById(R.id.ResetBT);
        ResetBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                count = 0;
                countExTV.setText(Integer.toString(count));

            }
        });

        ImageButton b = (ImageButton) findViewById(R.id.buttonBackToMain);
        b.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent it = new Intent (getApplicationContext(),MainActivity.class);
                startActivity(it);
                finish();
            }
        });

        Button noiseButton = (Button) findViewById(R.id.noiseBT);
        noiseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Accelerometer2Activity.NOISE = Float.parseFloat(noiseEditText.getText().toString());

                if(Accelerometer2Activity.NOISE < 13)
                {
                    Accelerometer2Activity.NOISE = 13;
                }

                Toast.makeText(AccelerometerActivity.this, "Exercise Difficulty Value : " + Accelerometer2Activity.NOISE, Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void onResume() {
        super.onResume();
        sensorManager.registerListener(accelListener, sensor,
                400000);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(accelListener);
    }

    SensorEventListener accelListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }

        public void onSensorChanged(SensorEvent event) {

            float x = (float) event.values[0];
            float y = (float) event.values[1];
            float z = (float) event.values[2];



            textX.setText("X : " + (float)x);
            textY.setText("Y : " + (float)y);
            textZ.setText("Z : " + (float)z);
            //counttv.setText("Count = 0");


            if (!check) {
                mLastX = x;
                mLastY = y;
                mLastZ = z;
                textX.setText("0");
                textY.setText("0");
                textZ.setText("0");

                check = true;

            }
            else {

                deltaX = Math.abs(mLastX - x);

                deltaY = Math.abs(mLastY - y);

                Log.i("Test Delta", "deltaZ = " + deltaZ);
                deltaZ = Math.abs(mLastZ - z);

                if (deltaX < Accelerometer2Activity.NOISE) deltaX = 0;
                if (deltaY < Accelerometer2Activity.NOISE) deltaY = 0;
                if (deltaZ < Accelerometer2Activity.NOISE) deltaZ = 0;

                mLastX = x;
                mLastY = y;
                mLastZ = z;

                textX.setText("X : "+" "+Float.toString(deltaX));
                textY.setText("Y : "+" "+Float.toString(deltaY));
                textZ.setText("Z : "+" "+Float.toString(deltaZ));

                if (deltaX > 0.0 || deltaY > 0.0 || deltaZ > 0.0 ){
                    checkcount += 1;
                    if (checkcount == 2){
                        count += 1;
                        countExTV.setText(Integer.toString(count));
                        checkcount = 0;
                    }


                }
            }


        }



    };
}
