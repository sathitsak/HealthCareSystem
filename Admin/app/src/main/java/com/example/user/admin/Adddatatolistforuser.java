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
 * Created by user on 2/8/2016.
 */
public class Adddatatolistforuser  extends AsyncTask<Void,Void,String> {
    private Context sContext;
    private String uUrl;
    public Adddatatolistforuser(Context context,String url){
        super();
        sContext=context;
        uUrl=url;
    }

    @Override
    protected  String doInBackground(Void... params){
        String jsonString=JsonHttp.makeHttpRequest(uUrl);
        return jsonString;
    }
    @Override
    protected void onPostExecute(String jsonString){
        try {
            ArrayList<HashMap<String,String>> userList;
            JSONObject json=new JSONObject(jsonString);
            int success=json.getInt("success");

            if(success==1){
                JSONArray user=json.getJSONArray("user");
                userList=new ArrayList<HashMap<String,String>>();

                for(int i=0;i<user.length();i++){
                    JSONObject A_user=user.getJSONObject(i);

                    String id=A_user.getString("User_id");
                    String name=A_user.getString("Username");
                    String type=A_user.getString("Type");

                    HashMap<String,String> map=new HashMap<String,String>();
                    map.put("User_id",id);
                    map.put("Username",name);
                    map.put("Type",type);

                    userList.add(map);

                    String[] keys=new String[]{"User_id","Username","Type"};
                    int[] views=new int[]{
                            R.id.item_id_user,
                            R.id.item_name_user,
                            R.id.item_type_user
                    };

                    ListAdapter adapter=new SimpleAdapter(sContext,userList,R.layout.itemforuser,keys,views);
                        ((Deleteuser) sContext).list.setAdapter(adapter);
                }

            }else if(success==0){
                    ((Deleteuser) sContext).list.setAdapter(null);
                    String msg=json.getString("message");
                    Toast.makeText(sContext, msg, Toast.LENGTH_SHORT).show();
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
