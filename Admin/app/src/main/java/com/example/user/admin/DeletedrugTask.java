package com.example.user.admin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/7/2016.
 */
public class DeletedrugTask extends AsyncTask<Void,Void,String>{
    private  Context dContext;
    private String id;
    private String url="http://104.131.23.206/admin/deletedrug.php?id=";

    public DeletedrugTask(Context context,String id_d){
        dContext=context;
        id=id_d;
    }

    protected String doInBackground(Void... params){
        String url2=url+id;
        String jsonString=JsonHttp.makeHttpRequest(url2);
        //Log.d("aa", "bb" + jsonString);
        return jsonString;
    }

    protected void onPostExecute(String s) {
        try {
            JSONObject json=new JSONObject(s);

            String msg=json.getString("message");
            Toast.makeText(dContext, msg, Toast.LENGTH_SHORT).show();
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
