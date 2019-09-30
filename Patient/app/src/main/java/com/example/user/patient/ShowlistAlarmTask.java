package com.example.user.patient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 3/14/2016.
 */
public class ShowlistAlarmTask extends AsyncTask <String,Void,String>{
    Context mContext;

    public ShowlistAlarmTask(Context context){
        mContext=context;
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonString=JsonHttp.makeHttpRequest(params[0]);
        //Log.d("aa", "bb"+jsonString);
        return jsonString;
    }
    @Override
    protected void onPostExecute(String jsonString) {

        try {
            ArrayList<HashMap<String,String>> MyArrList;

            JSONArray data = new JSONArray(jsonString);

            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i1 = 0; i1 < data.length(); i1++){
                JSONObject c = data.getJSONObject(i1);

                // รับข้อมูลจาก database
                map = new HashMap<String, String>();
                // รับข้อมูลจาก database
                map = new HashMap<String, String>();
                map.put("Assign_id", c.getString("Assign_id"));
                map.put("amount", c.getString("amount"));
                map.put("Drug_name", c.getString("Drug_name"));
                map.put("drug_id", c.getString("drug_id"));
                //map.put("time", c.getString("time"));
                map.put("before_after", c.getString("before_after"));
                map.put("Picture_url", c.getString("Picture_url"));
                map.put("breakfast", c.getString("breakfast"));
                map.put("lunch", c.getString("lunch"));
                map.put("dinner", c.getString("dinner"));
                map.put("sleep", c.getString("sleep"));
                map.put("hour1", c.getString("hour1"));
                map.put("minute1", c.getString("minute1"));
                map.put("hour2", c.getString("hour2"));
                map.put("minute2", c.getString("minute2"));
                map.put("hour3", c.getString("hour3"));
                map.put("minute3", c.getString("minute3"));
                map.put("hour4", c.getString("hour4"));
                map.put("minute4", c.getString("minute4"));
                Log.d("aa", "" + map);
                MyArrList.add(map);

                String[] keys=new String[]{"Assign_id","Drug_name","before_after",
                        "amount","hour1","hour2","hour3","hour4","minute1",
                        "minute2","minute3","minute4"
                };

                int[] views=new int[]{
                        R.id.idassign,
                        R.id.drugname,
                        R.id.before_after,
                        R.id.amount,
                        R.id.hour1,
                        R.id.hour2,
                        R.id.hour3,
                        R.id.hour4,
                        R.id.minute1,
                        R.id.minute2,
                        R.id.minute3,
                        R.id.minute4
                };
                ListAdapter adapter=new SimpleAdapter(mContext,MyArrList,R.layout.item_alarm,keys,views);
                ((Alarmon) mContext).list.setAdapter(adapter);

            }
            //MyArrList.get(0).get("hour4");


        }catch (JSONException e){
            e.printStackTrace();
        }

    }

}
