package com.example.user.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2/6/2016.
 */
public class Infodrug extends Activity{
    String id;

    TextView nameV;
    TextView detailV;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infordrug);



        // Image url http://i936.photobucket.com/albums/ad202/basaraneko/Yuzurhua-oboro-muramasa-muramasa-the-demon-blade-27958240-1280-1024_zpshmibwx4e.jpg

        Intent i=getIntent();
        id=i.getStringExtra("ID_drug");

        showdata();



    }

    public void showdata(){
        String url="http://104.131.23.206/admin/infordrug.php?drug_id="+id;
     //   Log.d("aa","bb"+id);
        class InfordrugTask extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... params){
                String jsonString=JsonHttp.makeHttpRequest(params[0]);
                // Log.d("aa","bb"+jsonString);
                return jsonString;
            }

            @Override
            protected void onPostExecute(String s) {
                String name="0";
                String detail="0";
                String url_p="0";
                try {
                    JSONObject json=new JSONObject(s);

                    name=json.getString("Drug_name");
                    detail=json.getString("Drug_detail");
                    url_p=json.getString("Picture_url");


                }catch (JSONException e){
                    e.printStackTrace();
                }
                nameV=(TextView)findViewById(R.id.drugname);
                detailV=(TextView)findViewById(R.id.drugdetail);

                nameV.setText(name);
                detailV.setText(detail);
                int loader = R.drawable.loader;
                // Imageview to show
                ImageView image = (ImageView) findViewById(R.id.imageView);
                String image_url = url_p;
                //IMAGE FIX HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
                // ImageLoader class instance
                ImageLoader imgLoader = new ImageLoader(getApplicationContext());

                // whenever you want to load an image from url
                // call DisplayImage function
                // url - image url to load
                // loader - loader image, will be displayed before getting image
                // image - ImageView
                imgLoader.DisplayImage(image_url, loader, image);
                // Log.d("aa","bb");

            }
        }

        InfordrugTask task=new InfordrugTask();
        task.execute(url);
    }


}
