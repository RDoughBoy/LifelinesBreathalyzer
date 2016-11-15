package com.ceng319.lifelinesbreathalyzer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

        Button name1 = (Button) findViewById(R.id.Name1Button);
        Button name2 = (Button) findViewById(R.id.Name2Button);
        Button name3 = (Button) findViewById(R.id.Name3Button);
        Button name4 = (Button) findViewById(R.id.Name4Button);
        Button name5 = (Button) findViewById(R.id.Name5Button);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String con1_name = sharedPreferences.getString("Contact1_Name", "empty");
        final int con1_num = sharedPreferences.getInt("Contact1_Number", 0);
        String con2_name = sharedPreferences.getString("Contact2_Name", "empty");
        final int con2_num = sharedPreferences.getInt("Contact2_Number", 0);
        String con3_name = sharedPreferences.getString("Contact3_Name", "empty");
        final int con3_num = sharedPreferences.getInt("Contact3_Number", 0);
        String con4_name = sharedPreferences.getString("Contact4_Name", "empty");
        final int con4_num = sharedPreferences.getInt("Contact4_Number", 0);
        String con5_name = sharedPreferences.getString("Contact5_Name", "empty");
        final int con5_num = sharedPreferences.getInt("Contact5_Number", 0);

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
        if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
