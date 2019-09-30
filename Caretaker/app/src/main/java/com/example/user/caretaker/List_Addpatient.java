package com.example.user.caretaker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2/14/2016.
 */
public class List_Addpatient extends Activity{
    String id_c;
    ListView list;
    Button addpatient;
    String[] Cmd = {"ลบข้อมูลผู้ป่วย"};
    ArrayList<HashMap<String, String>> MyArrList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_addpatient);
        Intent i=getIntent();

        addpatient=(Button)findViewById(R.id.addpatient);
        list=(ListView)findViewById(R.id.listpatient);
        registerForContextMenu(list);
        id_c=i.getStringExtra("caretaker_id"); //รั�? id �?อ�? caretaker
       // Log.d("aa","bb"+id_c);

        addpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(List_Addpatient.this, Register_P.class);
                aa.putExtra("caretaker_id", id_c);
                startActivity(aa);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        shownamepatient();
    }
    private void shownamepatient(){
        String url="http://104.131.23.206/caretaker/showallpatient_c.php?";
        url+="caretaker_id="+id_c;

        class ShowpatientTask extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... params) {
                String jsonString=JsonHttp.makeHttpRequest(params[0]);
                //Log.d("aa","bb"+jsonString);
                return jsonString;
            }

            @Override
            protected void onPostExecute(String jsonString){

                try {
                    JSONArray data = new JSONArray(jsonString);
                    MyArrList = new ArrayList<HashMap<String, String>>();
                    HashMap<String, String> map;

                    for(int i = 0; i < data.length(); i++){
                        JSONObject c = data.getJSONObject(i);
                        map = new HashMap<String, String>();

                        map.put("User_id", c.getString("User_id"));
                        map.put("Username", c.getString("Username"));

                        MyArrList.add(map);
                        String[] keys=new String[]{"User_id","Username"};
                        int[] views=new int[]{
                                R.id.item_id,
                                R.id.item_name
                        };
                        ListAdapter adapter=new SimpleAdapter(List_Addpatient.this,MyArrList,R.layout.item,keys,views);
                        list.setAdapter(adapter);
                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String user_id = MyArrList.get(position).get("User_id").toString();

                                Intent aa = new Intent(List_Addpatient.this,Menu_patient.class);
                                aa.putExtra("caretaker_id", id_c);
                                aa.putExtra("patient_id", user_id);
                                startActivity(aa);

                            }
                        });
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }

        ShowpatientTask task=new ShowpatientTask();
        task.execute(url);

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderIcon(android.R.drawable.btn_star_big_on);
        menu.setHeaderTitle("คำสั่ง");
        String[] menuItems = Cmd;
        for (int i = 0; i<menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = Cmd;
        String CmdName = menuItems[menuItemIndex];
        String id_p;

        if ("ลบข้อมูลผู้ป่วย".equals(CmdName)) {

            String url = "http://104.131.23.206/caretaker/delete_p.php";
            id_p=MyArrList.get(info.position).get("User_id").toString();
            //Log.d("aa","bb"+id_p);
            url+="?Patient_id="+id_p;
            DeletepatientTask task=new DeletepatientTask(this);
            task.execute(url);
            shownamepatient();
        }
        return true;
    }
}
