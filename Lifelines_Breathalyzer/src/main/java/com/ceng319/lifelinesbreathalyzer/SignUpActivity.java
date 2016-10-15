package com.ceng319.lifelinesbreathalyzer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
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

        final EditText name_val = (EditText) findViewById(R.id.signupName); //name
        final EditText email_val = (EditText) findViewById(R.id.signupEmail); //email
        final EditText phone_val = (EditText) findViewById(R.id.signupPhone); //phone
        final EditText add_val = (EditText) findViewById(R.id.signupAddress); //address
        final EditText city_val = (EditText) findViewById(R.id.signupCity); //city
        final EditText pass_val = (EditText) findViewById(R.id.signupPassword); //password
        final EditText pass_confirm_val = (EditText) findViewById(R.id.signupPasswordConfirm); //password confirmation

        //VALIDATION OF INPUT
        name_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (name_val.getText().length() == 0){
                    name_val.setError("Name Cannot Be Empty");
                } else if(name_val.getText().length() > 0 && name_val.getText().length() <= 2) {
                    name_val.setError("2 or More Characters");
                } else if (!Pattern.matches("^[^0-9]+\\s[A-z]+$", name_val.getText().toString())) {
                    name_val.setError("Invalid Name");
                } else {
                    valid++;
                }
            }
        });

        email_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (email_val.getText().length() == 0) {
                    email_val.setError("Email Cannot Be Empty");
                } else if (!Pattern.matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}", email_val.getText().toString())) {
                    email_val.setError("Invalid Email");
                } else {
                    valid++;
                }
            }
        });

        phone_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (phone_val.getText().length() == 0){
                    phone_val.setError("Cannot Be Empty");
                } else if (phone_val.getText().length() < 10) {
                    phone_val.setError("Invalid Phone Number");
                } else {
                    valid++;
                }
            }
        });

        add_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(add_val.getText().length() == 0){
                    add_val.setError("Address Cannot Be Empty");
                } else if(!Pattern.matches("^\\d+\\s[A-z]+\\s[A-z]+", add_val.getText().toString())) {
                    add_val.setError("Invalid Address");
                } else {
                    valid++;
                }
            }
        });

        pass_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass_val.getText().length() == 0){
                    pass_val.setError("Password Cannot Be Empty");
                } else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", pass_val.getText().toString())) {
                    pass_val.setError("Must Contain a Number, an Uppercase, and Be Over 8 Characters");
                } else {
                    valid++;
                }
            }
        });

        pass_confirm_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pass_confirm_val.getText().length() == 0){
                    pass_confirm_val.setError("Password Cannot Be Empty");
                } else if (!pass_confirm_val.getText().toString().equals(pass_val.getText().toString())) {
                    pass_confirm_val.setError("Password Doesn't Match");
                } else {
                    valid++;
                }
            }
        });

        city_val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (city_val.getText().length() == 10){
                    valid++;
                } else if (!Pattern.matches("^[^0-9]+$", city_val.getText().toString())) {
                    city_val.setError("Cannot Be Empty");
                } else if(!Pattern.matches("^[^0-9]+$", city_val.getText().toString())){
                    city_val.setError("No Numbers");
                }
            }
        });

        Button createAccount = (Button)findViewById(R.id.signupButton);
        createAccount.setOnClickListener(new View.OnClickListener(){    //TODO write information to text file for testing
            public void onClick(View v){
                if (valid < 7){
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("1 or More Fields Are Invalid");
                    dlgAlert.setTitle("Error");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }else {
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
