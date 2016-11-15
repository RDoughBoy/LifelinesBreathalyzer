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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private boolean nameValid = false;
    private boolean emailValid = false;
    private boolean passValid = false;
    private boolean passConfirmValid = false;
    private boolean cityValid = false;
    private boolean addressValid = false;
    private boolean phoneValid = false;
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

        final Spinner province = (Spinner)findViewById(R.id.signupProvince);
        final Spinner licence = (Spinner)findViewById(R.id.signupLicence);

        name_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (name_val.getText().length() == 0) {
                    name_val.setError(getString(R.string.valid_name_empty));
                    nameValid = false;
                } else if (!Pattern.matches("^[^0-9][A-z]+\\s[A-z]+$", name_val.getText().toString())) {    //Must contain no numbers, contain a space, and be over 4 characters long
                    name_val.setError(getString(R.string.valid_name_not));
                    nameValid = false;
                } else if (name_val.getText().length() < 5){
                    name_val.setError(getString(R.string.valid_name_not));
                    nameValid = false;
                } else {
                    nameValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        email_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (email_val.getText().length() == 0) {
                    email_val.setError(getString(R.string.valid_email_empty));
                    emailValid = false;
                } else if (!Pattern.matches("[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}", email_val.getText().toString())) {       //Must be in "name@domain.com" format
                    email_val.setError(getString(R.string.valid_email_not));
                    emailValid = false;
                } else {
                    emailValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        phone_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phone_val.getText().length() == 0) {
                    phone_val.setError(getString(R.string.valid_phone_empty));
                    phoneValid = false;
                } else if (phone_val.getText().length() < 10) {                     //Must be 10 digits long
                    phone_val.setError(getString(R.string.valid_phone_not));
                    phoneValid = false;
                } else {
                    phoneValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        add_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (add_val.getText().length() == 0) {
                    add_val.setError(getString(R.string.valid_address_empty));
                    addressValid = false;
                } else if (!Pattern.matches("^\\d+\\s[A-z]+\\s[A-z]+", add_val.getText().toString())) {          //Must be in "123 Name Road" format
                    add_val.setError(getString(R.string.valid_address_not));
                    addressValid = false;
                } else {
                    addressValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pass_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pass_val.getText().length() == 0) {
                    pass_val.setError(getString(R.string.valid_password_empty));
                    passValid = false;
                } else if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", pass_val.getText().toString())) {       //Must contain a number, an uppercase, and be 8 characters long
                    pass_val.setError(getString(R.string.valid_password_not));
                    passValid = false;
                } else {
                    passValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pass_confirm_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pass_confirm_val.getText().length() == 0) {
                    pass_confirm_val.setError(getString(R.string.valid_password_empty));
                    passConfirmValid = false;
                } else if (!pass_confirm_val.getText().toString().equals(pass_val.getText().toString())) {      //Must match with password
                    pass_confirm_val.setError(getString(R.string.valid_password_check));
                    passConfirmValid = false;
                } else {
                    passConfirmValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        city_val.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (city_val.getText().length() == 0) {
                    city_val.setError(getString(R.string.valid_city_empty));
                    cityValid = false;
                } else if (!Pattern.matches("^[^0-9]+$", city_val.getText().toString())) {            //Must contain no numbers
                    city_val.setError(getString(R.string.valid_city_not));
                    cityValid = false;
                } else {
                    cityValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final DBAdapter dbEntry = new DBAdapter(this);

        Button createAccount = (Button) findViewById(R.id.signupButton);
        createAccount.setOnClickListener(new View.OnClickListener() {    //TODO write information to text file for testing
            public void onClick(View v) {
                if (!nameValid || !emailValid || !passValid || !passConfirmValid || !cityValid || !addressValid || !phoneValid) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage(getString(R.string.dialog_invalid));
                    dlgAlert.setTitle(getString(R.string.dialog_error));
                    dlgAlert.setPositiveButton(getString(R.string.OK), null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                } else if(dbEntry.insertUser(name_val.getText().toString(), email_val.getText().toString(), pass_val.getText().toString(), city_val.getText().toString(), province.getSelectedItem().toString(), licence.getSelectedItem().toString(), add_val.getText().toString(), Integer.parseInt(phone_val.getText().toString())) == -1){
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage(getString(R.string.signup_email_error));
                    dlgAlert.setTitle(getString(R.string.dialog_error));
                    dlgAlert.setPositiveButton(getString(R.string.OK), null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                }
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
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
