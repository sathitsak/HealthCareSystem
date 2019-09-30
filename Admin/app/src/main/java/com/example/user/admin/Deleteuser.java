package com.example.user.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by user on 2/7/2016.
 */
public class Deleteuser extends Activity  implements AdapterView.OnItemClickListener {
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteuser);
        list=(ListView)findViewById(R.id.listView2);
        list.setOnItemClickListener(this);

    }


    @Override
    public void onResume(){
        super.onResume();
        shownameuser();
    }

    private void shownameuser(){
        String url="http://104.131.23.206/admin/selectall_username.php";
        Adddatatolistforuser task=new Adddatatolistforuser(this,url);
        task.execute();

    }

    @Override
    public void onItemClick(AdapterView<?> parent,View v,int position,long id){
        final String rowId=((TextView)v.findViewById(R.id.item_id_user)).getText().toString();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("Are you sure.");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               DeleteuserTask task=new DeleteuserTask(Deleteuser.this,rowId);
                task.execute();
                shownameuser();
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
}
