package com.example.dell.hajjcoin;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;


public class TransferActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText phone,amount;
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        phone   = findViewById(R.id.phone_number);
        sendBtn = findViewById(R.id.send_btn);
        amount  = findViewById(R.id.amount);

        sendBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send_btn:
                performTrans();
                finish();
                break;
        }
    }
    private void performTrans(){
        String userID = "1";
        String amount_m = amount.getText().toString();
        String phoneNum = phone.getText().toString();
        String url      = HelperClass.url_transfer+userID+"&phone2="+phoneNum+"&amount="+amount_m;

        HttpGetRequest getRequest = new HttpGetRequest(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String res) {
                Gson gson = new Gson();
                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
                status.s  = gson.fromJson(res,status.class);
                finish();
            }

            @Override
            public void UpdateProgress(int flag) {

            }
        });

        getRequest.execute(url);

    }
}
