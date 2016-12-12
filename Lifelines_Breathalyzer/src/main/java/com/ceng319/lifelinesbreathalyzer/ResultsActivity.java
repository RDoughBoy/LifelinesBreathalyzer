/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResultsActivity extends AppCompatActivity {


    private static final int REQUEST_CALL = 1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    Button button_call, button_taxi, button_hotel, button_past;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This is used to detect if it is a tablet. Then tells it to be landscape only.
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        // get Firebase User
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        button_call = (Button) findViewById(R.id.ResultsOption1);
        button_taxi = (Button) findViewById(R.id.ResultsOption2);
        button_hotel = (Button) findViewById(R.id.ResultsOption3);
        button_past = (Button) findViewById(R.id.ResultsOption4);

        if (mFirebaseUser != null) {
            button_past.setVisibility(View.VISIBLE);
        } else {
            button_past.setVisibility(View.GONE);
        }

        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String con1_name = sharedPreferences.getString("Contact1_Name", "empty");
                String con2_name = sharedPreferences.getString("Contact2_Name", "empty");
                String con3_name = sharedPreferences.getString("Contact3_Name", "empty");
                String con4_name = sharedPreferences.getString("Contact4_Name", "empty");
                String con5_name = sharedPreferences.getString("Contact5_Name", "empty");
                if(con1_name == "empty" && con2_name == "empty" && con3_name == "empty" && con4_name == "empty" && con5_name == "empty"){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResultsActivity.this);
                    builder.setMessage(R.string.dialog_configure_contacts)
                            .setTitle(R.string.dialog_error)
                            .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ResultsActivity.this, ContactsActivity.class);
                                    startActivity(intent);
                                }
                            });
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(ResultsActivity.this, CallActivity.class);
                    if (ContextCompat.checkSelfPermission(ResultsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ResultsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        startActivity(intent);
                    }
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
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=Hotels");
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
        if(id == R.id.action_contacts) {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CALL:
            {
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), getString(R.string.PGrant), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(ResultsActivity.this, CallActivity.class);
                    //startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), getString(R.string.PDecline), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
