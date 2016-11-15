/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Context context = this;
    private Boolean loginValid = false;
    UserSessionManager session;
    CheckBox remember = (CheckBox)findViewById(R.id.loginRemember);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new UserSessionManager(getApplicationContext());

        final EditText email = (EditText)findViewById(R.id.loginEmail);
        final EditText pass = (EditText)findViewById(R.id.loginPassword);
        TextView signup = (TextView)findViewById(R.id.loginSignUp) ;
        Button login = (Button)findViewById(R.id.loginButton);

        signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        final DBAdapter dbEntry = new DBAdapter(this);
        dbEntry.open();

        login.setOnClickListener(new View.OnClickListener(){    //TODO read and validate info from text file to use for testing
            public void onClick(View v){
                Cursor cursor = dbEntry.searchLogin(email.getText().toString());
                if (cursor.moveToFirst()) {
                    do {
                        if(cursor.getString(0).equalsIgnoreCase(email.getText().toString()) && cursor.getString(1).equalsIgnoreCase(pass.getText().toString())){
                            loginValid = true;
                        }
                    } while (cursor.moveToNext());
                }

                if(!loginValid){
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage(getString(R.string.login_invalid));
                    dlgAlert.setTitle(getString(R.string.dialog_error));
                    dlgAlert.setPositiveButton(getString(R.string.OK), null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                }

                session.createUserLoginSession(email.getText().toString(), pass.getText().toString());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
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

}
