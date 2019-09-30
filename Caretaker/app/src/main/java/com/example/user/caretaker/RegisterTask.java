package com.example.user.caretaker;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/14/2016.
 */
public class RegisterTask extends AsyncTask<Void,Void,String> {
    private Context mContext;
    private String url="http://104.131.23.206/caretaker/register.php";
    ContentValues param;

    public RegisterTask(Context context,ContentValues params){
        mContext=context;
        param=params;

        url+="?user="+param.get("user")+"&pass="+param.get("pass")+"&email="+param.get("email")+"&tel="+params.get("tel");
    }

    @Override
    protected String doInBackground(Void... params) {
        String jsonString=JsonHttp.makeHttpRequest(url);
      //  Log.d("aa","bb"+jsonString);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject json=new JSONObject(s);
            String sts=json.getString("StatusID");
            String err=json.getString("Error");
            if(sts.equals("0")){
                Toast.makeText(mContext, err, Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
            }
           // Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
