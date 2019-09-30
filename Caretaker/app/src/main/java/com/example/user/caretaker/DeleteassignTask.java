package com.example.user.caretaker;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 3/4/2016.
 */
public class DeleteassignTask extends AsyncTask<String,Void,String>{
    Context aContext;

    public DeleteassignTask(Context context){
        aContext=context;
    }

    @Override
    protected String doInBackground(String[] params) {
        String jsonString=JsonHttp.makeHttpRequest(params[0]);
        // Log.d("aa", "bb" + jsonString);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString){
        try {
            JSONObject c = new JSONObject(jsonString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(aContext, "Delete success", Toast.LENGTH_LONG).show();

    }
}
