package com.example.user.patient;

import android.os.AsyncTask;

/**
 * Created by user on 3/15/2016.
 */
public class SavependingTask extends AsyncTask<String,Void,String>{
    @Override
    protected String doInBackground(String... params) {
        String jsonString=JsonHttp.makeHttpRequest(params[0]);
        //Log.d("aa", "bb"+jsonString);
        return jsonString;
    }
}
