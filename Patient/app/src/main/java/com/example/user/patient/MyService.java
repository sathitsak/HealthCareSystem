package com.example.user.patient;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by user on 1/27/2016.
 */
public class MyService extends Service{
    //TextView textX, textY, textZ;
    SensorManager sensorManager;
    Sensor sensor;
    String text;
    String getTel;
    String getStopcall;
    int test = 2;
    private Handler handler = new Handler();

    public static boolean check_fall_exercise;

    public MyService() {

        //	Log.e("Log Test","in My Service2 1");
        FallWarningActivity a = new FallWarningActivity();
        getTel = a.NumberHelper;
        //	Log.e("Log Test","in My Service2 2");

		/*Log.e("Log Test","in My Service2 3");
		System.out.println("Num = " + getTel);

		FallWarningActivity b = new FallWarningActivity();
		getStopcall = b.Stopcall;
		System.out.println("Stop = " + getStopcall);*/

        check_fall_exercise = true;

    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Log Test", "in My Service2 onBIND");
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate() {
        Log.e("Log Test","in My Service2 Oncreate");
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.fallwarning2page);
        //text = getIntent().getExtras().getString("MyValue");


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //textX = (TextView) findViewById(R.id.textX);
        //textY = (TextView) findViewById(R.id.textY);
        //textZ = (TextView) findViewById(R.id.textZ);


        //Button b = (Button) findViewById(R.id.buttonBackToMain);
        //	b.setOnClickListener(new View.OnClickListener() {

        //public void onClick(View v) {
        //	Intent it = new Intent (getApplicationContext(),MainActivity.class);
        //	startActivity(it);
        //}
        //	}
        //);
        Log.e("Log Test","in My Service2 Oncreate 2");


        //if(test == 2)
        //{
        onResume();
        //test--;
        //}




    }

    public void onResume() {
        //super.onResume();
        sensorManager.registerListener(accelListener, sensor,
                400000);
    }

    public void onStop() {
        //super.onStop();
        Log.e("Log Test","in My Service2 On Stop");
        sensorManager.unregisterListener(accelListener);
        accelListener = null;
    }


    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        sensorManager.unregisterListener(accelListener);
        accelListener = null;

        super.onDestroy();
    }





    SensorEventListener accelListener = new SensorEventListener() {



        public void onAccuracyChanged(Sensor sensor, int acc) {
            Log.e("Log Test","in My Service2 ON Sensor Accu Change");
        }

        public void onSensorChanged(SensorEvent event) {

            Log.e("Log Test","in My Service2 ON Sensor Change");

            int x = Math.abs((int) event.values[0]);
            int y = Math.abs((int) event.values[1]);
            int z = Math.abs((int) event.values[2]);

            //textX.setText("X : " + x);
            //textY.setText("Y : " + y);
            //textZ.setText("Z : " + z);

		/*if(test == 0)
		{
			onStop();
			test++;
		}*/


	/*if(getStopcall == "STOP")
	{

		onStop();

	}*/


            if(x > 15 || y > 15 || z > 15 )
            {
                onStop();
                Log.e("Log Test","in My Service2 CALL STOP");
                Log.e("Log Test","in My Service2 CALL1");
                Intent it = new Intent (MyService.this,FallWarning3Activity.class);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.e("Log Test","in My Service2 CALL2");
                it.putExtra("MyValue", getTel);
                Log.e("Log Test","in My Service2 CALL3");
                startActivity(it);
                ;


            }




        }
    };

}
