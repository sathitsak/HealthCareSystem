package com.example.user.admin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2/6/2016.
 */
public class Adddatatolist extends AsyncTask<Void,Void,String>{
    private Context sContext;
    private String sUrl;
    private String identify;

    public Adddatatolist(Context context,String url,String i ){
        super();
        sContext=context;
        sUrl=url;
        identify=i;
    }

    @Override
    protected  String doInBackground(Void... params){
        String jsonString=JsonHttp.makeHttpRequest(sUrl);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString){
        try {
            ArrayList<HashMap<String,String>> drugList;
            JSONObject json=new JSONObject(jsonString);
            int success=json.getInt("success");

            if(success==1){
                JSONArray drug=json.getJSONArray("drug");
                drugList=new ArrayList<HashMap<String,String>>();

                for(int i=0;i<drug.length();i++){
                    JSONObject A_drug=drug.getJSONObject(i);

                    String id=A_drug.getString("Drug_id");
                    String name=A_drug.getString("Drug_name");

                    HashMap<String,String> map=new HashMap<String,String>();
                    map.put("Drug_id",id);
                    map.put("Drug_name",name);

                    drugList.add(map);

                    String[] keys=new String[]{"Drug_id","Drug_name"};
                    int[] views=new int[]{
                         R.id.item_id,
                            R.id.item_name
                    };

                    ListAdapter adapter=new SimpleAdapter(sContext,drugList,R.layout.item,keys,views);
                  if(identify.equals("showdrug")) {
                      ((Showdrug) sContext).list.setAdapter(adapter);
                  }else if(identify.equals("deletedrug")){
                      ((Deletedrug) sContext).list.setAdapter(adapter);
                  }
                }

            }else if(success==0){
                if(identify.equals("showdrug")) {
                    ((Showdrug) sContext).list.setAdapter(null);
                    String msg=json.getString("message");
                    Toast.makeText(sContext,msg,Toast.LENGTH_SHORT).show();
                }else if(identify.equals("deletedrug")){
                    ((Deletedrug) sContext).list.setAdapter(null);
                    String msg=json.getString("message");
                    Toast.makeText(sContext,msg,Toast.LENGTH_SHORT).show();
                }

            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
