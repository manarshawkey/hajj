package com.example.dell.hajjcoin;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

class mInfo{
    String id;
    String storename;
    String longitude;
    String latitude;
    static mInfo mInfo = new mInfo();
}
public class TransActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private Toolbar toolbar;
    private MapView mMapView;
    private String mid;
    private TextView store, amount;

    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException e) {
                Log.e("Error", "Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        mid = getIntent().getExtras().getString("mid");

        store  = findViewById(R.id.store);
        amount = findViewById(R.id.amount);

        amount.setText(getIntent().getExtras().getString("amount"));

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transaction Log");
        mMapView = findViewById(R.id.fragment_embedded_map_view_mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchMerchant();

    }

    private void fetchMerchant(){

        String url = HelperClass.url_merchant + mid;

        HttpGetRequest getRequest = new HttpGetRequest(new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(String res) {
                Gson gson = new Gson();
                mInfo.mInfo = gson.fromJson(res,mInfo.class);
                store.setText(mInfo.mInfo.storename);
            }

            @Override
            public void UpdateProgress(int flag) {

            }
        });

        getRequest.execute(url);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)        // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
