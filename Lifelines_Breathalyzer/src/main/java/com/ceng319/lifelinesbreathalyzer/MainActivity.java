/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    SharedPreferences sharedPreferences;
    static Boolean alreadyExecuted = false;
    Button button_login, button_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This is used to detect if it is a tablet. Then tells it to be landscape only.
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        // execute once when the app starts
        if(!alreadyExecuted) {
            checkRememberMe();  // check if remember me is set and logout user if not
            alreadyExecuted = true;
        }

        // get Firebase User
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        button_test = (Button) findViewById(R.id.BeginTest);
        Button button_test = (Button) findViewById(R.id.mainAtest);
        Button heart = (Button) findViewById(R.id.mainH);
        Button results = (Button) findViewById(R.id.Results);

        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestAlcoholActivity.class);
                startActivity(intent);
            }
        });
        button_login = (Button) findViewById(R.id.Log);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HeartRateActivity.class);
                startActivity(intent);
            }
        });

        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PastResultsActivity.class);
                startActivity(intent);
            }
        });

        final Button button_login = (Button) findViewById(R.id.Log);

        // set button to logout if user is logged in
        if (mFirebaseUser != null) {
            button_login.setText(getString(R.string.logout));
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFirebaseAuth.signOut();
                    button_login.setText(getString(R.string.login));
                    button_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        } else {
            // set button to login if user not logged in
            button_login.setText(getString(R.string.login));
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
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
    // Handle the Back Key
    public void onBackPressed() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(getString(R.string.dialog_exit));
        dlgAlert.setTitle(getString(R.string.dialog_exit_title));
        dlgAlert.setNegativeButton(getString(R.string.dialog_no), null);
        dlgAlert.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(1);
            }
        });
        dlgAlert.create().show();
    }

    //check if remember me is set
    public void checkRememberMe(){
        boolean checkBoxValue;
        button_login = (Button) findViewById(R.id.Log);

        //get sharedpreferences for the boolean "Remember_Me"
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        checkBoxValue = sharedPreferences.getBoolean("Remember_Me", false);

        //sign out user if "Remember_Me" is false and set login button
        if (!checkBoxValue) {
            mFirebaseAuth.signOut();
            button_login.setText(getString(R.string.login));
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}


