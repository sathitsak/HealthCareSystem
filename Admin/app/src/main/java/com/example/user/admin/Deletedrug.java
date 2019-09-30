package com.example.user.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by user on 2/3/2016.
 */
public class Deletedrug extends Activity implements AdapterView.OnItemClickListener {
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletedrug);
        list=(ListView)findViewById(R.id.listView);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        shownamedrug();
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View v,int position,long id){
        final String rowId=((TextView)v.findViewById(R.id.item_id)).getText().toString();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Are you sure.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeletedrugTask task=new DeletedrugTask(Deletedrug.this,rowId);
                task.execute();
                shownamedrug();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,int which){

            }
        });

        builder.show();
        /*
        String rowId=((TextView)v.findViewById(R.id.item_id)).getText().toString();
        DeletedrugTask task=new DeletedrugTask(this,rowId);
        task.execute();*/
    }

    private void shownamedrug(){
       String url="http://104.131.23.206/admin/selectall_drugname.php";
        Adddatatolist task=new Adddatatolist(this,url,"deletedrug");
        task.execute();
    }
}
