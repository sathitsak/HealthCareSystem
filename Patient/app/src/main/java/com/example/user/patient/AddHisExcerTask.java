package com.example.user.patient;

import android.os.AsyncTask;

/**
 * Created by user on 4/8/2016.
 */
public class AddHisExcerTask extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        String jsonString=JsonHttp.makeHttpRequest(params[0]);
        //Log.d("aa", "bb"+jsonString);
        return jsonString;
    }
}
