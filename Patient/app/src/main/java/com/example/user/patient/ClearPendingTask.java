package com.example.user.patient;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 3/15/2016.
 */
public class ClearPendingTask extends AsyncTask<Void,Void,String> {
    String url="http://104.131.23.206/patient/load_clear_pending.php";
    Context mContext;
    public ClearPendingTask(Context context){
        mContext=context;

    }

    @Override
    protected String doInBackground(Void... params) {
        String jsonString=JsonHttp.makeHttpRequest(url);
        //Log.d("bb","bb"+jsonString);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
       // Log.d("bb","bb");
        super.onPostExecute(s);
        try {
            JSONArray data = new JSONArray(s);


            for(int i1 = 0; i1 < data.length(); i1++){
                JSONObject c = data.getJSONObject(i1);
                int id=Integer.parseInt(c.getString("id"));

                Intent ww = new Intent(mContext, NotifyService.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, id, ww,PendingIntent.FLAG_CANCEL_CURRENT);
                ((ChooseMenu) mContext).alarmManager.cancel(pendingIntent);
            }


        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
