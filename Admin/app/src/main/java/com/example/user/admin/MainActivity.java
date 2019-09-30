package com.example.user.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String result;
    private String url="http://104.131.23.206/admin/login.php";

    Button btnLogin;
    EditText txtUser;
    EditText txtPass;
    static String chk="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // btnLogin
         btnLogin = (Button) findViewById(R.id.btnLogin);
         txtUser = (EditText)findViewById(R.id.txtUsername);
         txtPass = (EditText)findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
            login();
}

public void login(){
    String username = txtUser.getText().toString().trim();
    String password = txtPass.getText().toString().trim();
    String urlfull=url+"?" + "aUser=" + username + "&aPass=" + password;

    class UserLoginClass extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params){
            String jsonString=JsonHttp.makeHttpRequest(params[0]);
           // Log.d("aa","bb"+jsonString);
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
                Toast.makeText(MainActivity.this, "ล็อกอินสำเร็จ", Toast.LENGTH_SHORT).show();
                Intent newActivity = new Intent(MainActivity.this,Choosemenu.class);
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

        }catch (JSONException e){
            e.printStackTrace();
        }
        return  status;

    }
}
