package com.example.user.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 2/1/2016.
 */
public class Drugmenu extends Activity implements View.OnClickListener{

    Button showdrug;
    Button adddrug;
    Button deletedrug;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menudrug);

        showdrug=(Button)findViewById(R.id.showdrug);
        adddrug=(Button)findViewById(R.id.adddrug);
        deletedrug=(Button)findViewById(R.id.deletedrug);

        showdrug.setOnClickListener(this);
        adddrug.setOnClickListener(this);
        deletedrug.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.showdrug){
            Intent aa=new Intent(this,Showdrug.class);
            startActivity(aa);
        }else if(v.getId()==R.id.adddrug){
            Intent aa=new Intent(this,Adddrug.class);
            startActivity(aa);
        }else if(v.getId()==R.id.deletedrug){
            Intent aa=new Intent(this,Deletedrug.class);
            startActivity(aa);
        }

    }
}
