package com.example.user.caretaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user on 2/13/2016.
 */
public class Register_C extends Activity {
    EditText username;
    EditText password;
    EditText repassword;
    EditText email;
    EditText tel;
    Button register;
    Button back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        username=(EditText)findViewById(R.id.username_c);
        password=(EditText)findViewById(R.id.password_c);
        repassword=(EditText)findViewById(R.id.repassword_c);
        email=(EditText)findViewById(R.id.email_c);
        tel=(EditText)findViewById(R.id.phone_c);
        register=(Button)findViewById(R.id.register);
        back=(Button)findViewById(R.id.back);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void register(){
        String user=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        String repass=repassword.getText().toString().trim();
        String e_mail=email.getText().toString().trim();
        String phonenum=tel.getText().toString().trim();
        ContentValues params=new ContentValues();


        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Error! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("Close", null);

        if(user.length()==0){
            ad.setMessage("กรุณาใส่ชื่อแอคเคาท์อีกครั้ง");
            ad.show();
            username.requestFocus();
        }else if(pass.length()==0){
            ad.setMessage("กรุณาใส่พาสเวิร์ดอีกครั้ง");
            ad.show();
            password.requestFocus();
        }else if(repass.length()==0){
            ad.setMessage("กรุณาใส่รีพาสเวิร์ดอีกครั้ง");
            ad.show();
            repassword.requestFocus();
        }else if(!pass.equals(repass)){
            ad.setMessage("กรุณาใส่พาสเวิร์ดให้ตรงกับรีพาสเวิร์ด");
            ad.show();
            repassword.requestFocus();
        }else  if(e_mail.length()==0){
            ad.setMessage("กรุณาใส่อีเมลล์อีกครั้ง");
            ad.show();
            email.requestFocus();
        } else if(!Utility.validate(e_mail)) {
            ad.setMessage("ไม่ถูกหลักของอีเมลล์");
            ad.show();
            email.requestFocus();
        }else if(phonenum.length()==0){
            ad.setMessage("กรุณาใส่เบอร์โทร");
            ad.show();
            tel.requestFocus();
        }else{

            params.put("user",user);
            params.put("pass",pass);
            params.put("email",e_mail);
            params.put("tel",phonenum);

            RegisterTask task=new RegisterTask(this,params);
            task.execute();
        }
    }

}
