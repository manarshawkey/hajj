package com.example.dell.hajjcoin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

class top{
    String status;
    String id;
    static top t;
}
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText phoneNum, password;
    private TextView error;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNum = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        error    = findViewById(R.id.login_error);

        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_btn:
                if(CheckFields())
                {
                    checkDB();

                }
                break;
        }
    }

    private boolean CheckFields(){
        String phone    = phoneNum.getText().toString();
        String passwrod = password.getText().toString();

        if (phone.isEmpty()){
            error.setText("Phone Field is required");
            return false;
        }
        if (passwrod.isEmpty())
        {
            error.setText("Password Field is required");
            return false;
        }

        return true;
    }

    private void checkDB(){
        String phone    = phoneNum.getText().toString();
        String passwrod = password.getText().toString();

        String url = HelperClass.url_verify + phone +"&pass="+passwrod;

        HttpGetRequest getRequest = new HttpGetRequest(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String res) {
                Gson gson = new Gson();
                top.t = gson.fromJson(res,top.class);

                if(top.t.status.equals("1"))
                {
                    Intent i = new Intent(getApplicationContext(), homeActivity.class);
                    finish();
                    startActivity(i);
                }
                else error.setText("Phone or Password is incorrect");
            }

            @Override
            public void UpdateProgress(int flag) {

            }
        });

        getRequest.execute(url);
    }
}
