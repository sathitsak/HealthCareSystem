package com.example.user.patient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2/15/2016.
 */
public class JsonHttp {
    public static String makeHttpRequest(String url){
        String strResult="";


        try {
            URL u=new URL(url);
            HttpURLConnection con=(HttpURLConnection)u.openConnection();
             // Log.d("aa","bb"+con);
            // Log.d("aa","bb"+con.getInputStream());
            strResult=readStream(con.getInputStream());
        }catch (Exception e){
            Log.d("log_err", "Error in http connection " + e.toString());
            // e.printStackTrace();
        }

        return strResult;
    }

    private static String readStream(InputStream in){
        //Log.d("aa","pass");
        BufferedReader reader=null;
        StringBuilder sb=new StringBuilder();
        try{
            reader=new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line=reader.readLine())!=null){
                sb.append(line+"\n");
            }

        }catch (IOException e){
            Log.e("log_tag", "Error converting result " + e.toString());
            //e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  sb.toString();

    }
}
