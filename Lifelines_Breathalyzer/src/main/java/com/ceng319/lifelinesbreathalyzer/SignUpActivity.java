/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private int valid = 0;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //VALIDATION OF INPUT
        final EditText name_val = (EditText) findViewById(R.id.signupName); //name
        final EditText email_val = (EditText) findViewById(R.id.signupEmail); //email
        final EditText phone_val = (EditText) findViewById(R.id.signupPhone); //phone
        final EditText add_val = (EditText) findViewById(R.id.signupAddress); //address
        final EditText city_val = (EditText) findViewById(R.id.signupCity); //city
        final EditText pass_val = (EditText) findViewById(R.id.signupPassword); //password
        final EditText pass_confirm_val = (EditText) findViewById(R.id.signupPasswordConfirm); //password confirmation

        name_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (name_val.getText().length() == 0){
                    name_val.setError(getString(R.string.valid_name_empty));
                } else if (!Pattern.matches("^[^0-9][A-z]+\\s[A-z]+$", name_val.getText().toString()) && name_val.getText().length() < 5) {    //Must contain no numbers, contain a space, and be over 4 characters long
                    name_val.setError(getString(R.string.valid_name_not));
                } else {
                    valid++;
                }
            }
        });

        email_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (email_val.getText().length() == 0) {
                    email_val.setError(getString(R.string.valid_email_empty));
                } else if (!Pattern.matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}", email_val.getText().toString())) {       //Must be in "name@domain.com" format
                    email_val.setError(getString(R.string.valid_email_not));
                } else {
                    valid++;
                }
            }
        });

        phone_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (phone_val.getText().length() == 0){
                    phone_val.setError(getString(R.string.valid_phone_empty));
                } else if (phone_val.getText().length() < 10) {                     //Must be 10 digits long
                    phone_val.setError(getString(R.string.valid_phone_not));
                } else {
                    valid++;
                }
            }
        });

        add_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(add_val.getText().length() == 0){
                    add_val.setError(getString(R.string.valid_address_empty));
                } else if(!Pattern.matches("^\\d+\\s[A-z]+\\s[A-z]+", add_val.getText().toString())) {          //Must be in "123 Name Road" format
                    add_val.setError(getString(R.string.valid_address_not));
                } else {
                    valid++;
                }
            }
        });

        pass_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass_val.getText().length() == 0){
                    pass_val.setError(getString(R.string.valid_password_empty));
                } else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", pass_val.getText().toString())) {       //Must contain a number, an uppercase, and be 8 characters long
                    pass_val.setError(getString(R.string.valid_password_not));
                } else {
                    valid++;
                }
            }
        });

        pass_confirm_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass_confirm_val.getText().length() == 0){
                    pass_confirm_val.setError(getString(R.string.valid_password_empty));
                } else if (!pass_confirm_val.getText().toString().equals(pass_val.getText().toString())) {      //Must match with password
                    pass_confirm_val.setError(getString(R.string.valid_password_check));
                } else {
                    valid++;
                }
            }
        });

        city_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (city_val.getText().length() == 0){
                    city_val.setError(getString(R.string.valid_city_empty));
                } else if(!Pattern.matches("^[^0-9]+$", city_val.getText().toString())){            //Must contain no numbers
                    city_val.setError(getString(R.string.valid_city_not));
                } else {
                    valid++;
                }
            }
        });

        Button createAccount = (Button)findViewById(R.id.signupButton);
        createAccount.setOnClickListener(new View.OnClickListener(){    //TODO write information to text file for testing
            public void onClick(View v){
                if (valid < 6){
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage(getString(R.string.dialog_invalid));
                    dlgAlert.setTitle(getString(R.string.dialog_error));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }else {
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
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
