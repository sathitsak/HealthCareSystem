package com.example.user.patient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    Button login;
    EditText txtUser;
    EditText txtPass;
    static String chk="1";
    String id_p;
    static String url="http://104.131.23.206/patient/login_p.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_p);

        login=(Button)findViewById(R.id.login);
        txtUser=(EditText)findViewById(R.id.editText);
        txtPass=(EditText)findViewById(R.id.editText2);

        SharedPreferences sharedPref=getPreferences(Context.MODE_PRIVATE);
        int b=sharedPref.getInt("a", 0);
       // Log.d("aa","bb"+b);
        String id_pa=sharedPref.getString("id_p","no_id");
        if(b==1){
            Intent newActivity = new Intent(MainActivity.this,ChooseMenu.class);
            newActivity.putExtra("patient_id",id_pa);
            startActivity(newActivity);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });
    }

    public void login(){

        String username = txtUser.getText().toString().trim();
        String password = txtPass.getText().toString().trim();
        String urlfull=url+"?" + "pUser=" + username + "&pPass=" + password;
     //  Log.d("aa", "bb");
        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params){
                String jsonString=JsonHttp.makeHttpRequest(params[0]);
                return jsonString;
            }
            @Override
            protected void onPostExecute(String s) {
                chk=ParseJson(s);
                if(chk.equals("0"))
                {
                    Toast.makeText(MainActivity.this, "ล็อกอินผิดพลาด", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mempatient(id_p);
                    Toast.makeText(MainActivity.this, "ล็อกอินสำเร็จ", Toast.LENGTH_SHORT).show();

                    Intent newActivity = new Intent(MainActivity.this,ChooseMenu.class);
                    newActivity.putExtra("patient_id",id_p);
                    startActivity(newActivity);
                }
                txtUser.setText("");
                txtPass.setText("");
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(urlfull);
    }

    private String ParseJson(String jsonString){
        String status="0";
        try {
            JSONObject json=new JSONObject(jsonString);
            status=json.getString("StatusID");
            id_p=json.getString("id_patient");

        }catch (JSONException e){
            e.printStackTrace();
        }
        return  status;

    }

    private void mempatient(String id_pa){
        SharedPreferences sharedPref=getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPref.edit();
        editor.putString("id_p", id_pa);
        editor.putInt("a", 1);
        editor.commit();
    }
}
