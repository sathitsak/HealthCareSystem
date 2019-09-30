package com.example.user.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 2/7/2016.
 */
public class Usermenu extends Activity implements View.OnClickListener{
    Button delete;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuuser);

        delete=(Button)findViewById(R.id.deleteuser);

        delete.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.deleteuser){
            Intent a=new Intent(this,Deleteuser.class);
            startActivity(a);
        }
    }
}
