package com.example.user.admin;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 2/3/2016.
 */
public class Showdrug extends Activity implements AdapterView.OnItemClickListener {
    ListView list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showdrug);
         list=(ListView)findViewById(R.id.listView_drug);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent,View v,int position,long id){
        //หา id ของแถวข้อมูลที่จะแสดง
        String rowId=((TextView)v.findViewById(R.id.item_id)).getText().toString();

        Intent newActivity = new Intent(this,Infodrug.class);
        newActivity.putExtra("ID_drug",rowId);
        startActivity(newActivity);
    }



    @Override
    public void onResume(){
        super.onResume();
        shownamedrug();
    }

    private void shownamedrug(){
        String url="http://104.131.23.206/admin/selectall_drugname.php";
        Adddatatolist task=new Adddatatolist(this,url,"showdrug");
        task.execute();
    }

}
