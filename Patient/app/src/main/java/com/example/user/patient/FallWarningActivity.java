package com.example.user.patient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by user on 1/27/2016.
 */
public class FallWarningActivity extends Activity {
    public static String NumberHelper;
    public static String Stopcall;
    //public static SensorEventListener a;
    //SensorManager s;
    phoneDBClass dbHelper;
    SQLiteDatabase database;
    Cursor mCursor;


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fallwarningpage);

        //	Toast.makeText(FallWarningActivity.this, "Helper Telephone Number is : " + NumberHelper, Toast.LENGTH_LONG).show();
        final AlertDialog.Builder dDialog = new AlertDialog.Builder(this);
        final EditText inputTelephoneNumber = (EditText) findViewById(R.id.inputHelperTelephoneNumberET);

        dbHelper = new phoneDBClass(this);
        database = dbHelper.getWritableDatabase();

        mCursor = database.rawQuery("SELECT count(*) FROM " + dbHelper.TABLE_NAME , null);
//mCursor = database.rawQuery("SELECT " + dbHelper.COL_TEL + " FROM " + dbHelper.TABLE_NAME, null);
        mCursor.moveToFirst();
        final int iCount = mCursor.getInt(0);

        //if(NumberHelper == null )
        //{database.execSQL("INSERT INTO " + dbHelper.TABLE_NAME + " (" + dbHelper.COL_TEL + ")  VALUES (11);");};
        //{};


// SAVE INPUT PHONE NUMBER
        ImageButton buttonOK = (ImageButton) findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                NumberHelper = inputTelephoneNumber.getText().toString();

                if(NumberHelper.length()==0){
                    NumberHelper="1669";
                }
                Toast.makeText(FallWarningActivity.this, "Helper Telephone Number is : " + NumberHelper, Toast.LENGTH_SHORT).show();

                if (NumberHelper.length() != 0)
                {
                    if (iCount == 0 )
                    {

                        database.execSQL("INSERT INTO " + dbHelper.TABLE_NAME + " (" + dbHelper.COL_TEL + ")  VALUES (1"+ NumberHelper +");");
                        //Toast.makeText(FallWarningActivity.this, "Insert Database " + dbHelper.COL_TEL, Toast.LENGTH_LONG).show();
                        //database.execSQL("UPDATE " + dbHelper.TABLE_NAME  + " SET " + dbHelper.COL_TEL + "= 1" + NumberHelper +
                        // " WHERE " + dbHelper.COL_TEL + "!='xxxx';");
                    }
                    else if (iCount > 0)
                    {
                        database.execSQL("UPDATE " + dbHelper.TABLE_NAME  + " SET " + dbHelper.COL_TEL + "= 1" + NumberHelper +
                                " WHERE " + dbHelper.COL_TEL + "!='xxxx';");
                    }
                    ///NumberHelperTV.setText(inputTelephoneNumber.getText().toString());
                    ///Intent it = new Intent (getApplicationContext(),MyService2.class);
                    ///it.putExtra("MyValue", NumberHelper);
                    ///startActivity(it);


                }
                else
                {
                    dDialog.setTitle("WARNING!");
                    dDialog.setMessage("Please Input Helper Number");
                    dDialog.setPositiveButton("Close", null);
                    dDialog.show();
                }
            }
        });




        final TextView ToggleTV = (TextView) findViewById(R.id.ToggleTV);
        final ToggleButton toggleBtn = (ToggleButton)findViewById(R.id.toggleButton);
//---------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------
        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toggleBtn.isChecked() == true) //START FALL WARNING FUNCTION
                {
                    //if(NumberHelper.length() == 0 )
                    if(NumberHelper == null || NumberHelper.length() == 0)
                    {

                        dDialog.setTitle("WARNING!");
                        dDialog.setMessage("Please Input Helper Number");
                        dDialog.setPositiveButton("Close", null);
                        dDialog.show();
                        toggleBtn.setChecked(false);

                        //AlertDialog ad = adb.create();
                        //ad.setMessage("Hi Hi");
                        //ad.show();
                    }

                    //Log.e("Log Test","NOT YET MyService");
                    else
                    {
                        ToggleTV.setText("Activate Fall Warning Function");
                        ToggleTV.setTextColor(Color.GREEN);
                        startService(new Intent(FallWarningActivity.this, MyService.class));
                        Toast.makeText(FallWarningActivity.this, "Activate Fall Warning Function", Toast.LENGTH_SHORT).show();
                    }
                    //Log.e("Log Test","YET MyService");
                }
                else //STOP FALL WARNING FUNCTION
                {
                    //Stopcall = "STOP";
                    //System.out.print("STOP IN STOP" + Stopcall);
                    //if(NumberHelper.length() == 0 )
                    if(NumberHelper == null || NumberHelper.length() == 0)
                    {
                        dDialog.setTitle("WARNING!");
                        dDialog.setMessage("Please Input Helper Number");
                        dDialog.setPositiveButton("Close", null);
                        dDialog.show();
                        toggleBtn.setChecked(true);
                    }
                    //Log.e("Log Test","NOT YET MyService stop");
                    else
                    {
                        ToggleTV.setText("Deactivate Fall Warning Function");
                        ToggleTV.setTextColor(Color.RED);
                        //startService(new Intent(FallWarningActivity.this, MyService.class));
                        stopService(new Intent(FallWarningActivity.this, MyService.class));
                        //Log.e("Log Test","YET MyService stop");
                        Toast.makeText(FallWarningActivity.this, "Deactivate Fall Warning Function", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
/*
//START FALL WARNING FUNCTION
Button buttonStartService = (Button) findViewById(R.id.buttonStartService) ;
buttonStartService.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View v) {
		Log.e("Log Test","NOT YET MyService");
		startService(new Intent(FallWarningActivity.this, MyService.class));
		Toast.makeText(FallWarningActivity.this, "Activate Fall Warning Function", Toast.LENGTH_LONG).show();
		Log.e("Log Test","YET MyService");
		//Intent it2 = new Intent (getApplicationContext(),MyService.class);
		//startActivity(it2);

		// TODO Auto-generated method stub

	}
});

//STOP FALL WARNING FUNCTION
Button buttonStopService = (Button) findViewById(R.id.buttonStopService) ;
buttonStopService.setOnClickListener(new View.OnClickListener() {

	@Override
	public void onClick(View v) {
		//Stopcall = "STOP";
		//System.out.print("STOP IN STOP" + Stopcall);
		Log.e("Log Test","NOT YET MyService stop");
		//startService(new Intent(FallWarningActivity.this, MyService.class));
		stopService(new Intent(FallWarningActivity.this, MyService.class));
		Log.e("Log Test","YET MyService stop");
		Toast.makeText(FallWarningActivity.this, "Deactivate Fall Warning Function", Toast.LENGTH_LONG).show();

		//Intent it2 = new Intent (getApplicationContext(),MyService.class);
		//startActivity(it2);

	}
});
		*/

        ImageButton b = (ImageButton) findViewById(R.id.buttonBackToMain);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });




    }

    public void onPause() {
        super.onPause();
        dbHelper.close();
        database.close();
    }

}
