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
 * Created by user on 3/1/2016.
 */
public class StatusTask extends AsyncTask<Void,Void,String> {

    Context sContext;
    String id_p;
    String url="http://104.131.23.206/caretaker/viewhistorys.php?";

    public StatusTask(Context context, String id){
        sContext=context;
        id_p=id;
        url+="patient_id="+id_p;
    }
    @Override
    protected String doInBackground(Void... params) {
        String jsonString=JsonHttp.makeHttpRequest(url);
       // Log.d("aa", "bb" + jsonString);
        return jsonString;
    }


    @Override
    protected void onPostExecute(String jsonString){

        ArrayList<HashMap<String,String>> MyArrList;

        try {
            JSONArray data = new JSONArray(jsonString);

            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);
                map = new HashMap<String, String>();
                //map.put("User_id", c.getString("User_id"));
                map.put("History_id", c.getString("History_id"));
                map.put("Drug_name", c.getString("Drug_name"));
                map.put("Date1", c.getString("Date1"));
                map.put("Status", c.getString("Status"));
                map.put("Time", c.getString("Time"));

                MyArrList.add(map);
                String[] keys=new String[]{"History_id","Drug_name","Date1","Status","Time"};
                int[] views=new int[]{
                        R.id.historyID,
                        R.id.namedrug,
                        R.id.date,
                        R.id.status,
                        R.id.time};

                ListAdapter adapter=new SimpleAdapter(sContext,MyArrList,R.layout.item_his,keys,views);
                ((CheckStatus)sContext).list.setAdapter(adapter);

            }


        }catch (JSONException e){
            e.printStackTrace();
        }



    }
}
