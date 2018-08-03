package com.example.dell.hajjcoin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

class status{
    String status;
    static status s = new status();
}
public class ScanActivity extends AppCompatActivity implements View.OnClickListener {

    SurfaceView cameraPreview;
    int RequestCameraPermissionID = 1001;

    private Button pay;
    private EditText amount;
    private String merchantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();

        pay    = findViewById(R.id.pay_btn);
        amount = findViewById(R.id.amount);
        pay.setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        if(result != null)
        {
            if(result.getContents() == null)
            {
                Toast.makeText(this, "null res content", Toast.LENGTH_LONG).show();
            }
            else {
                merchantID = result.getContents();
                Toast.makeText(this, "HERE " + result.getContents(), Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(this, "null res", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.pay_btn:
                if(!amount.getText().toString().isEmpty())
                    Pay();

                break;
        }
    }

    private void Pay(){

        String userID = "1";
        String url = HelperClass.url_pay + merchantID +"&clientid="+userID + "&amount=" + amount;

        HttpGetRequest getRequest = new HttpGetRequest(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String res) {
                Gson gson = new Gson();
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
