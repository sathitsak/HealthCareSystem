package com.example.user.caretaker;

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

public class MainActivity extends AppCompatActivity {
    static String chk="1";
    static String url="http://104.131.23.206/caretaker/login_c.php";

    String id_c;
    Button login;
    Button register;
    EditText txtUser;
    EditText txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=(Button)findViewById(R.id.buttonlogin);
        register=(Button)findViewById(R.id.buttonregister);
        txtUser=(EditText)findViewById(R.id.editText);
        txtPass=(EditText)findViewById(R.id.editText2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Register_C.class);
                startActivity(i);
            }
        });
    }
    public void login(){

        String username = txtUser.getText().toString().trim();
        String password = txtPass.getText().toString().trim();
        String urlfull=url+"?" + "cUser=" + username + "&cPass=" + password;
        //Log.d("aa", "bb"+urlfull);
        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected String doInBackground(String... params){
                Log.d("aa", "bb"+"cancon");
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
                    Toast.makeText(MainActivity.this, "ล็อกอินสำเร็จ", Toast.LENGTH_SHORT).show();
                    Intent newActivity = new Intent(MainActivity.this,List_Addpatient.class);
                    newActivity.putExtra("caretaker_id",id_c);
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
            id_c=json.getString("id_caretaker");

        }catch (JSONException e){
            e.printStackTrace();
        }
        return  status;
    }
}

