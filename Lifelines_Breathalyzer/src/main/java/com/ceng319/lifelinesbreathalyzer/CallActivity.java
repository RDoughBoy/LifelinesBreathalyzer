/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CallActivity extends AppCompatActivity {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This is used to detect if it is a tablet. Then tells it to be landscape only.
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        Button name1 = (Button) findViewById(R.id.Name1Button);
        Button name2 = (Button) findViewById(R.id.Name2Button);
        Button name3 = (Button) findViewById(R.id.Name3Button);
        Button name4 = (Button) findViewById(R.id.Name4Button);
        Button name5 = (Button) findViewById(R.id.Name5Button);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String con1_name = sharedPreferences.getString("Contact1_Name", "empty");
        final String con1_num = sharedPreferences.getString("Contact1_Number", " ");
        String con2_name = sharedPreferences.getString("Contact2_Name", "empty");
        final String con2_num = sharedPreferences.getString("Contact2_Number", " ");
        String con3_name = sharedPreferences.getString("Contact3_Name", "empty");
        final String con3_num = sharedPreferences.getString("Contact3_Number", " ");
        String con4_name = sharedPreferences.getString("Contact4_Name", "empty");
        final String con4_num = sharedPreferences.getString("Contact4_Number", " ");
        String con5_name = sharedPreferences.getString("Contact5_Name", "empty");
        final String con5_num = sharedPreferences.getString("Contact5_Number", " ");

        name1.setText(con1_name + ": " + String.valueOf(con1_num));
        name2.setText(con2_name + ": " + String.valueOf(con2_num));
        name3.setText(con3_name + ": " + String.valueOf(con3_num));
        name4.setText(con4_name + ": " + String.valueOf(con4_num));
        name5.setText(con5_name + ": " + String.valueOf(con5_num));

        name1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + String.valueOf(con1_num)));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        name2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + String.valueOf(con2_num)));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        name3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + String.valueOf(con3_num)));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        name4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + String.valueOf(con4_num)));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        name5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + String.valueOf(con5_num)));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });

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
}
