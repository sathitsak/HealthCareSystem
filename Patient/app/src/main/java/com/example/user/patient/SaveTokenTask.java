package com.example.user.patient;

import android.os.AsyncTask;

/**
 * Created by user on 3/11/2016.
 */
public class SaveTokenTask extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        String jsonString=JsonHttp.makeHttpRequest(params[0]);
        return jsonString;
    }

}
