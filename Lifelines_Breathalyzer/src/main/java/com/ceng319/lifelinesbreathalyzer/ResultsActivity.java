package com.ceng319.lifelinesbreathalyzer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {


    private static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button button_call = (Button) findViewById(R.id.ResultsOption1);
        Button button_taxi = (Button) findViewById(R.id.ResultsOption2);
        Button button_hotel = (Button) findViewById(R.id.ResultsOption3);
        Button button_past = (Button) findViewById(R.id.ResultsOption4);

        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, CallActivity.class);
                if (ContextCompat.checkSelfPermission(ResultsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ResultsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    startActivity(intent);
                }
            }
        });

        button_taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, TaxiActivity.class);
                if (ContextCompat.checkSelfPermission(ResultsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ResultsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    startActivity(intent);
                }
            }
        });

        button_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, PastResultsActivity.class);
                startActivity(intent);
            }
        });

        button_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Search for Hotels in the area
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=HOTELS");
                Intent hotelSearch = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                hotelSearch.setPackage("com.google.android.apps.maps");
                startActivity(hotelSearch);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CALL:
            {
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(ResultsActivity.this, CallActivity.class);
                    //startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Need Permission To Use The Phone!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
