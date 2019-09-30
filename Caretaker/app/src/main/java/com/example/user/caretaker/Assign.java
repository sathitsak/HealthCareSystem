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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 3/2/2016.
 */
public class Assign extends Activity {
    String id_p;
    String id_c;
    ArrayList<HashMap<String, String>> MyArrList;
    String[] Cmd = {"Update","Delete"};
    ListView list;
    Button addassign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign);

        list=(ListView)findViewById(R.id.listAssign);
        registerForContextMenu(list);
        addassign=(Button)findViewById(R.id.assign_btn);
        Intent ii=getIntent();
        id_p=ii.getStringExtra("patient_id");
        id_c=ii.getStringExtra("caretaker_id");

        addassign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(Assign.this, Addassign.class);
                aa.putExtra("caretaker_id", id_c);
                aa.putExtra("patient_id", id_p);
                startActivity(aa);
            }
        });



    }

    @Override
    public void onResume(){
        super.onResume();
        showAssignment();
    }

    public void showAssignment(){
        String url="http://104.131.23.206/caretaker/showallassign_n.php?user_id=";
        url+=id_p;
       // Log.d("aa", "bb" + url);
        class ShowAssignTask extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... params) {
                String jsonString=JsonHttp.makeHttpRequest(params[0]);
             //   Log.d("aa", "bb"+jsonString);
                return jsonString;
            }

            @Override
            protected void onPostExecute(String jsonString){
                try {
                    JSONArray data = new JSONArray(jsonString);

                    MyArrList = new ArrayList<HashMap<String, String>>();
                    HashMap<String, String> map;

                    for(int i = 0; i < data.length(); i++) {
                        JSONObject c = data.getJSONObject(i);
                        map = new HashMap<String, String>();

                        map.put("Assign_id", c.getString("Assign_id"));
                        map.put("amount", c.getString("amount"));
                        map.put("Drug_name", c.getString("Drug_name"));
                        //map.put("time", c.getString("time"));
                        map.put("before_after", c.getString("before_after"));
                       // map.put("Picture_url", c.getString("Picture_url"));
                        map.put("hour1", c.getString("hour1"));
                        map.put("minute1", c.getString("minute1"));
                        map.put("hour2", c.getString("hour2"));
                        map.put("minute2", c.getString("minute2"));
                        map.put("hour3", c.getString("hour3"));
                        map.put("minute3", c.getString("minute3"));
                        map.put("hour4", c.getString("hour4"));
                        map.put("minute4", c.getString("minute4"));
                        MyArrList.add(map);

                        String[] keys=new String[]{"Assign_id","amount","Drug_name",
                                "before_after","hour1","minute1","hour2","minute2"
                                ,"hour3","minute3","hour4","minute4"
                        };

                        int[] views=new int[]{
                                R.id.assignID,
                                R.id.amount,
                                R.id.drugname,
                                R.id.before_after,
                                R.id.hour1,R.id.minute1,
                                R.id.hour2,R.id.minute2,
                                R.id.hour3,R.id.minute3,
                                R.id.hour4,R.id.minute4
                        };
                        ListAdapter adapter=new SimpleAdapter(Assign.this,MyArrList,R.layout.item_as,keys,views);
                        list.setAdapter(adapter);
                    }
                }catch (JSONException e){

                    e.printStackTrace();
                }


            }
        }

        ShowAssignTask task=new ShowAssignTask();
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
        String id_a;

        if ("Update".equals(CmdName)) {

            String Drug_name = MyArrList.get(info.position).get("Drug_name").toString();
            String ID = MyArrList.get(info.position).get("Assign_id").toString();

            Intent gg = new Intent(Assign.this,UpdateAsign.class);
            gg.putExtra("patient_id", id_p);
            gg.putExtra("caretaker_id", id_c);
            gg.putExtra("Drug_name", Drug_name);
            gg.putExtra("ID", ID);
            startActivity(gg);

        }else if("Delete".equals(CmdName)){

            String url = "http://104.131.23.206/caretaker/delete_as.php";
            id_a=MyArrList.get(info.position).get("Assign_id").toString();
            //Log.d("aa","bb"+id_p);
            url+="?id="+id_a;

            DeleteassignTask task=new DeleteassignTask(this);
            task.execute(url);
            showAssignment();
        }
        return true;
    }



}
