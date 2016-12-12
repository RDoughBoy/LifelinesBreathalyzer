/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PastResultsActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  //TODO Local DB for when user is logged out
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This is used to detect if it is a tablet. Then tells it to be landscape only.
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final TextView avgBAC = (TextView)findViewById(R.id.AVGBAC);
        final TextView avgBPM = (TextView)findViewById(R.id.AVGheart);
        final TextView textScrollable1 = (TextView)findViewById(R.id.PastBAClevels);
        final TextView textScrollable2 = (TextView)findViewById(R.id.PastBPMLevels);
        //This is to enable scrolling on scrollview
        textScrollable1.setMovementMethod(new ScrollingMovementMethod());
        textScrollable2.setMovementMethod(new ScrollingMovementMethod());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mFirebaseUser != null) {
            mUserId = mFirebaseUser.getUid();

            // get past BAC levels and display average
            mDatabase.child("users").child(mUserId).child("pastBAC").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String pastBAC = (dataSnapshot.getValue(String.class));
                    textScrollable1.setText(pastBAC);
                    convertStringToArray(pastBAC);
                    String[] arr = convertStringToArray(pastBAC);
                    double total = 0;
                    for (int i = 0; i < arr.length; i++){
                        total += Double.parseDouble(arr[i]);
                    }
                    double avg = total / arr.length;
                    avgBAC.setText(String.format("%.4f", avg) + "%");
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String pastBAC = (dataSnapshot.getValue(String.class));
                    textScrollable1.setText(pastBAC);
                    convertStringToArray(pastBAC);
                    String[] arr = convertStringToArray(pastBAC);
                    double total = 0;
                    for (int i = 0; i < arr.length; i++){
                        total += Double.parseDouble(arr[i]);
                    }
                    double avg = total / arr.length;
                    avgBAC.setText(String.format("%.4f", avg) + "%");
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            // get past BPM levels and display average
            mDatabase.child("users").child(mUserId).child("pastBPM").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String pastBPM = (dataSnapshot.getValue(String.class));
                    textScrollable2.setText(pastBPM);
                    String[] arr = convertStringToArray(pastBPM);
                    double total = 0;
                    for (int j = 0; j < arr.length; j++){
                        total += Integer.parseInt(arr[j]);
                    }
                    double avg = total / arr.length;
                    avgBPM.setText(String.format("%.2f", avg) + " BPM");
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String pastBPM = (dataSnapshot.getValue(String.class));
                    textScrollable2.setText(pastBPM);
                    String[] arr = convertStringToArray(pastBPM);
                    double total = 0;
                    for (int j = 0; j < arr.length; j++){
                        total += Integer.parseInt(arr[j]);
                    }
                    double avg = total / arr.length;
                    avgBPM.setText(String.format("%.2f", avg) + " BPM");
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
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

    // methods to convert arrays to strings and vice versa
    public static String strSeparator = "\\r?\\n";
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str += array[i];
            if(i < array.length - 1){
                str += strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
