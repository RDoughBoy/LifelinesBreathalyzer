/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.regex.Pattern;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private boolean nameValid = false, emailValid = false,
            passValid = false, passConfirmValid = false,
            cityValid = false, addressValid = false,
            phoneValid = false;
    Context context = this;
    EditText name_val, email_val, phone_val, add_val ,
            city_val, pass_val, pass_confirm_val;
    Spinner province, licence;
    Button createAccount;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    private String mUserId;
    private FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This is used to detect if it is a tablet. Then tells it to be landscape only.
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        // get Firebase user, authentication and database
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //VALIDATION OF INPUT
        name_val = (EditText) findViewById(R.id.signupName); //name
        email_val = (EditText) findViewById(R.id.signupEmail); //email
        phone_val = (EditText) findViewById(R.id.signupPhone); //phone
        add_val = (EditText) findViewById(R.id.signupAddress); //address
        city_val = (EditText) findViewById(R.id.signupCity); //city
        pass_val = (EditText) findViewById(R.id.signupPassword); //password
        pass_confirm_val = (EditText) findViewById(R.id.signupPasswordConfirm); //password confirmation
        province = (Spinner)findViewById(R.id.signupProvince);
        licence = (Spinner)findViewById(R.id.signupLicence);

        // name validation
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

        // email validation
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

        // phone validation
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

        // address validation
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

        // password validation
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

        // password confirmation validation
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

        // city validation
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

        createAccount = (Button) findViewById(R.id.signupButton);
        createAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // display alert dialog if any of the fields are invalid
                if (!nameValid || !emailValid || !passValid || !passConfirmValid || !cityValid || !addressValid || !phoneValid) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage(getString(R.string.dialog_invalid));
                    dlgAlert.setTitle(getString(R.string.dialog_error));
                    dlgAlert.setPositiveButton(getString(R.string.OK), null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return;
                } else {
                    // create a user account with email and password
                    mFirebaseAuth.createUserWithEmailAndPassword(email_val.getText().toString(), pass_val.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // add data to the Firebase database for the user
                                mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                mUserId = mFirebaseUser.getUid();
                                mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_name)).setValue(name_val.getText().toString());
                                mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_city)).setValue(city_val.getText().toString());
                                mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_province)).setValue(province.getSelectedItem().toString());
                                mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_licence)).setValue(licence.getSelectedItem().toString());
                                mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_address)).setValue(add_val.getText().toString());
                                mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_phone)).setValue(phone_val.getText().toString());

                                // go to main
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage(task.getException().getMessage())
                                        .setTitle(R.string.dialog_error)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
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
        if(id == R.id.action_contacts) {
            Intent intent = new Intent(this, ContactsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
