package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    String TAG="LoginPage";
    Button login_button,cancel_button;
    EditText name,password;
    String tempPass ="pass";
    String user="user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().setTitle("Login");

        login_button=(Button)findViewById(R.id.login_button);
        cancel_button=(Button)findViewById(R.id.cancel_button);
        name=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Clicked");
                init();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {

        Log.v(TAG,"user name:" + name.getText().toString() +"  pass : " + password.getText().toString());

        if (name.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast.makeText(this, "Please Enter user name!", Toast.LENGTH_SHORT).show();
        }else if (name.getText().toString().equalsIgnoreCase(user) && password.getText().toString().equalsIgnoreCase(tempPass)){
            Toast.makeText(this, "Welcome to Home Screen!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,HomePage.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Authentication not working!", Toast.LENGTH_SHORT).show();
        }
    }
}