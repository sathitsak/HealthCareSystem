package com.example.user.admin;

import android.app.Activity;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by user on 2/1/2016.
 */
public class Adddrug extends Activity implements View.OnClickListener{
    EditText Name;
    EditText Detail;
    Button Add;
    Button Cancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddrug);
        Name=(EditText)findViewById(R.id.namedrug);
        Detail=(EditText)findViewById(R.id.detaildrug);
        Add=(Button)findViewById(R.id.add);
        Cancel=(Button)findViewById(R.id.cancel);

        Add.setOnClickListener(this);
        Cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ContentValues params=new ContentValues();
        String a=Name.getText().toString().trim();
        String b=Detail.getText().toString().trim();

        String url="http://104.131.23.206/admin/admin_insert.php?"+"dName="+a+"&dDetail="+b;

        if(v.getId()==R.id.add){
            AddDrugTask task=new AddDrugTask(this);
            task.execute(url);

        }else if(v.getId()==R.id.cancel){
            finish();
        }
    }
}

