package com.example.user.patient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by user on 1/27/2016.
 */
public class FallWarning3Activity extends Activity {
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }

    phoneDBClass dbHelper;
    SQLiteDatabase database;
    Cursor mCursor;
    int a;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fallwarning3page);
        // Toast.makeText(FallWarning3Activity.this, "MyValue = " + getIntent().getExtras().getString("MyValue"), Toast.LENGTH_LONG).show();
        // String selectQuery = "SELECT TEL FROM phoneDBClass.TABLE_NAME WHERE TEL>0";
        //  Cursor mCursor = database.rawQuery(selectQuery, null);
        // Log.i("TEST TAG", getIntent().getExtras().getString("MyValue"));


        dbHelper = new phoneDBClass(this);
        database = dbHelper.getWritableDatabase();

        mCursor = database.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.COL_TEL.length() + "> 0 ", null);
        mCursor.moveToFirst();
        String Tel_Num2 = mCursor.getString(mCursor.getColumnIndex("TEL"));
        Tel_Num2 = Tel_Num2.substring ( 1, Tel_Num2.length() );
        //Toast.makeText(FallWarning3Activity.this, "Tel_Num" + Tel_Num, Toast.LENGTH_LONG).show();

        final CheckBox chkBox = (CheckBox)findViewById(R.id.chkBoxCall);
        chkBox.setVisibility(View.GONE);
        String Tel_Num1 = getIntent().getExtras().getString("MyValue");

        final MediaPlayer playMedia = MediaPlayer.create(this,R.raw.siren2min);
        playMedia.start();

        /// b = Integer.parseInt(Tel_Num1);
	/*   boolean Tel_Test = Tel_Num1.isEmpty();

        if(Tel_Test == false)
       	{
   		Intent it = new Intent (getApplicationContext(),MainActivity.class);
   		startActivity(it);
       	}
	 */
        // Toast.makeText(FallWarning3Activity.this, "Tel_Num1 = " + Tel_Num1, Toast.LENGTH_LONG).show();

        if (chkBox.isChecked() == false)
        {

            TelephonyManager telephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);



            if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                String msg = "Sorry, this device can't make phone calls!";
                Toast.makeText(FallWarning3Activity.this, msg, Toast.LENGTH_SHORT)
                        .show();
                return;
            }


            //       TextView txtPhoneNumber = (TextView) findViewById(R.id.NumberHelperTV);
            // String uri = "tel:" + Tel_Num1;
            String uri = "tel:" + Tel_Num2;
            // Toast.makeText(FallWarning3Activity.this, "Tel_Num1 = " + Tel_Num1, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
            startActivity(intent);

            MyService.check_fall_exercise = false;
            //Toast.makeText(FallWarning3Activity.this, "Tel_Num2" + Tel_Num2, Toast.LENGTH_LONG).show();
            stopService(new Intent(FallWarning3Activity.this, MyService.class));






            ImageButton b = (ImageButton) findViewById(R.id.buttonBackToMain);
            b.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent it = new Intent (getApplicationContext(),MainActivity.class);
                    startActivity(it);
                    finish();
                    playMedia.release();
                }
            });

            ImageButton mute = (ImageButton) findViewById(R.id.buttonMute);
            mute.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    playMedia.release();

                }
            });

        }

    }
    public void onWarp(){
        Intent it = new Intent (getApplicationContext(),MainActivity.class);
        startActivity(it);
    }

    public void onPause() {
        super.onPause();
        dbHelper.close();
        database.close();
    }


}
