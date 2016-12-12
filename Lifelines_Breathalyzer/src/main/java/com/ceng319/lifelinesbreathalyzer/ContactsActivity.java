/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ContactsActivity extends AppCompatActivity {
    private int valid = 0;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This is used to detect if it is a tablet. Then tells it to be landscape only.
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
       // sharedPreferences.edit().clear().commit();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Button submit = (Button)findViewById(R.id.button);

        final EditText name = (EditText) findViewById(R.id.editText);
        final EditText num = (EditText) findViewById(R.id.editText2);
        final EditText name2 = (EditText) findViewById(R.id.editText3);
        final EditText num2 = (EditText) findViewById(R.id.editText4);
        final EditText name3 = (EditText) findViewById(R.id.editText5);
        final EditText num3 = (EditText) findViewById(R.id.editText6);
        final EditText name4 = (EditText) findViewById(R.id.editText7);
        final EditText num4 = (EditText) findViewById(R.id.editText8);
        final EditText name5 = (EditText) findViewById(R.id.editText9);
        final EditText num5 = (EditText) findViewById(R.id.editText10);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (name.getText().length() == 0){
                    name.setError(getString(R.string.valid_name_empty));
                } else if (!Pattern.matches("^[^0-9][A-z]+\\s[A-z]+$", name.getText().toString())) {    //Must contain no numbers, contain a space, and be over 4 characters long
                    name.setError(getString(R.string.valid_name_not));
                } else {
                    valid++;
                }
            }
        });
        num.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (num.getText().length() == 0){
                    num.setError(getString(R.string.valid_phone_empty));
                } else if (num.getText().length() < 10) {                     //Must be 10 digits long
                    num.setError(getString(R.string.valid_phone_not));
                } else {
                    valid++;
                }
            }
        });
        name2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (name2.getText().length() == 0){
                    name2.setError(getString(R.string.valid_name_empty));
                } else if (!Pattern.matches("^[^0-9][A-z]+\\s[A-z]+$", name2.getText().toString())) {    //Must contain no numbers, contain a space, and be over 4 characters long
                    name2.setError(getString(R.string.valid_name_not));
                } else {
                    valid++;
                }
            }
        });
        num2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (num2.getText().length() == 0){
                    num2.setError(getString(R.string.valid_phone_empty));
                } else if (num.getText().length() < 10) {                        //Must be 10 digits long
                    num2.setError(getString(R.string.valid_phone_not));
                } else {
                    valid++;
                }
            }
        });
        name3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (name3.getText().length() == 0){
                    name3.setError(getString(R.string.valid_name_empty));
                } else if (!Pattern.matches("^[^0-9][A-z]+\\s[A-z]+$", name3.getText().toString())) {    //Must contain no numbers, contain a space, and be over 4 characters long
                    name3.setError(getString(R.string.valid_name_not));
                } else {
                    valid++;
                }
            }
        });
        num3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (num3.getText().length() == 0){
                    num3.setError(getString(R.string.valid_phone_empty));
                } else if (num3.getText().length() < 10) {                        //Must be 10 digits long
                    num3.setError(getString(R.string.valid_phone_not));
                } else {
                    valid++;
                }
            }
        });
        name4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (name4.getText().length() == 0){
                    name4.setError(getString(R.string.valid_name_empty));
                } else if (!Pattern.matches("^[^0-9][A-z]+\\s[A-z]+$", name4.getText().toString())) {    //Must contain no numbers, contain a space, and be over 4 characters long
                    name4.setError(getString(R.string.valid_name_not));
                } else {
                    valid++;
                }
            }
        });
        num4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (num4.getText().length() == 0){
                    num4.setError(getString(R.string.valid_phone_empty));
                } else if (num4.getText().length() < 10) {                       //Must be 10 digits long
                    num.setError(getString(R.string.valid_phone_not));
                } else {
                    valid++;
                }
            }
        });
        name5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (name5.getText().length() == 0){
                    name5.setError(getString(R.string.valid_name_empty));
                } else if (!Pattern.matches("^[^0-9][A-z]+\\s[A-z]+$", name5.getText().toString())) {    //Must contain no numbers, contain a space, and be over 4 characters long
                    name5.setError(getString(R.string.valid_name_not));
                } else {
                    valid++;
                }
            }
        });
        num5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (num5.getText().length() == 0){
                    num5.setError(getString(R.string.valid_phone_empty));
                } else if (num5.getText().length() < 10) {                      //Must be 10 digits long
                    num5.setError(getString(R.string.valid_phone_not));
                } else {
                    valid++;
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (valid < 10){
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                    dlgAlert.setMessage(getString(R.string.dialog_invalid));
                    dlgAlert.setTitle(getString(R.string.dialog_error));
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }else {
                    SharedPreferences sharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("Contact1_Name", name.getText().toString());
                    editor.putString("Contact1_Number", num.getText().toString());//Integer.parseInt(num.getText().toString()));
                    editor.putString("Contact2_Name", name2.getText().toString());
                    editor.putString("Contact2_Number", num2.getText().toString());//Integer.parseInt(num.getText().toString()));
                    editor.putString("Contact3_Name", name3.getText().toString());
                    editor.putString("Contact3_Number", num3.getText().toString()); //Integer.parseInt(num3.getText().toString()));
                    editor.putString("Contact4_Name", name4.getText().toString());
                    editor.putString("Contact4_Number", num4.getText().toString());//Integer.parseInt(num4.getText().toString()));
                    editor.putString("Contact5_Name", name5.getText().toString());
                    editor.putString("Contact5_Number", num5.getText().toString());//Integer.parseInt(num5.getText().toString()));
                    editor.apply();
                    createContact(name.getText().toString(),num.getText().toString());
                    createContact(name2.getText().toString(),num2.getText().toString());
                    createContact(name3.getText().toString(),num3.getText().toString());
                    createContact(name4.getText().toString(),num4.getText().toString());
                    createContact(name5.getText().toString(),num5.getText().toString());
                    Toast.makeText(getApplicationContext(),
                            "Contacts Saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);

                }
            }
        });

    }
    private void createContact(String name, String phone) {
        ContentResolver cr = getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String existName = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (existName.contains(name)) {
                    Toast.makeText(this,"The contact name: " + name + " already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "accountname@gmail.com")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build());
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                .build());


        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Toast.makeText(this, "Created a new contact with name: " + name + " and Phone No: " + phone, Toast.LENGTH_SHORT).show();

    }

}
