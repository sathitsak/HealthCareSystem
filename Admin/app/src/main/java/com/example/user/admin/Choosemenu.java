package com.example.user.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 1/30/2016.
 */

public class Choosemenu extends Activity implements View.OnClickListener{
    Button drug;
    Button user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menudrug_user);

        drug=(Button)findViewById(R.id.aboutdrugs);
        user=(Button)findViewById(R.id.aboutuser);

        drug.setOnClickListener(this);
        user.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.aboutdrugs){
            Intent a=new Intent(this,Drugmenu.class);
            startActivity(a);
        }else if(v.getId()==R.id.aboutuser){
            Intent a=new Intent(this,Usermenu.class);
            startActivity(a);
        }

    }
}
