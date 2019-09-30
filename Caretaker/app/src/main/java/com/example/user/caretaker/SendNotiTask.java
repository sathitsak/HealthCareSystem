package com.example.user.caretaker;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by user on 3/20/2016.
 */
public class SendNotiTask extends AsyncTask<Void,Void,String>{
    String id_p;
    String url="http://104.131.23.206/caretaker/send_noti2.php?patient_id=";
    public SendNotiTask(String id){
        id_p=id;
        url+=id_p;
        Log.d("aa","bb"+url);
    }
    @Override
    protected String doInBackground(Void... params) {
        String jsonString=JsonHttp.makeHttpRequest(url);
        //  Log.d("aa","bb"+jsonString);
        return jsonString;
    }
}
