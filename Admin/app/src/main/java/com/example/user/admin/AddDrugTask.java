package com.example.user.admin;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/1/2016.
 */
public class AddDrugTask extends AsyncTask<String,Void,String>{
    Context adddrugContext;

    public AddDrugTask(Context c){
        super();
        adddrugContext=c;
    }
    @Override
    protected String doInBackground(String... params) {
        String jsonString=JsonHttp.makeHttpRequest(params[0]);
       // Log.d("aa","bb"+jsonString);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString){

        String msg;
        try {
           // Log.d("aa","bb"+jsonString);
            JSONObject json=new JSONObject(jsonString);
           // Log.d("aa2","bb"+json);
             msg=json.getString("message");
          //  Log.d("aa3","bb"+msg);
            //Log.d("aa","bb"+msg);
            Toast.makeText(adddrugContext,msg,Toast.LENGTH_SHORT).show();
        }catch (JSONException e){
          //  e.printStackTrace();
            Log.d("aa","bb"+e);
        }
    }
}
