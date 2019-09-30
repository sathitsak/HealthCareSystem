package com.example.user.caretaker;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/15/2016.
 */
public class DeletepatientTask extends AsyncTask <String,Void,String> {
    Context lContext;

    public DeletepatientTask(Context context){
        lContext=context;

    }
    @Override
    protected String doInBackground(String... params) {
        String jsonString=JsonHttp.makeHttpRequest(params[0]);
       // Log.d("aa", "bb" + jsonString);
        return jsonString;
    }
    protected void onPostExecute(String jsonString){
        try {
            JSONObject c = new JSONObject(jsonString);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(lContext, "Delete success", Toast.LENGTH_LONG).show();

    }

}
