package com.example.dell.hajjcoin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button signinBtn , signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupBtn =  findViewById(R.id.signup_btn);
        signinBtn =  findViewById(R.id.login_btn);

        signinBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId())
        {

            case R.id.signup_btn:
                 i = new Intent(this, SignUp.class);
                startActivity(i);
                break;
            case R.id.login_btn:
                 i = new Intent(this, LoginActivity.class);
                 startActivity(i);
                break;
        }

    }
}
