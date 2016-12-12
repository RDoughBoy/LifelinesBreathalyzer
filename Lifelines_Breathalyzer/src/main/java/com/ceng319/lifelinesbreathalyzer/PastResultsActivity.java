/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.Intent;
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
    TextView avgBAC, avgBPM, tvPastBAC, tvPastBPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get Firebase user, authentication and database
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        avgBAC = (TextView)findViewById(R.id.AVGBAC);
        avgBPM = (TextView)findViewById(R.id.AVGheart);
        tvPastBAC = (TextView)findViewById(R.id.PastBAClevels);
        tvPastBPM = (TextView)findViewById(R.id.PastBPMLevels);

        //This is to enable scrolling on scrollview
        tvPastBAC.setMovementMethod(new ScrollingMovementMethod());
        tvPastBPM.setMovementMethod(new ScrollingMovementMethod());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //if user is logged in get the past data
        if (mFirebaseUser != null) {
            mUserId = mFirebaseUser.getUid();

            // get past BAC levels and display average
            mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_pastBAC)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String pastBAC = (dataSnapshot.getValue(String.class));
                    tvPastBAC.setText(pastBAC);
                    convertStringToArray(pastBAC);
                    String[] arr = convertStringToArray(pastBAC);
                    double total = 0;
                    for (int i = 0; i < arr.length; i++) {
                        total += Double.parseDouble(arr[i]);
                    }
                    double avg = total / arr.length;
                    avgBAC.setText(String.format("%.4f", avg) + "%");
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String pastBAC = (dataSnapshot.getValue(String.class));
                    tvPastBAC.setText(pastBAC);
                    convertStringToArray(pastBAC);
                    String[] arr = convertStringToArray(pastBAC);
                    double total = 0;
                    for (int i = 0; i < arr.length; i++) {
                        total += Double.parseDouble(arr[i]);
                    }
                    double avg = total / arr.length;
                    avgBAC.setText(String.format("%.4f", avg) + "%");
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            // get past BPM levels and display average
            mDatabase.child(getString(R.string.firebase_users)).child(mUserId).child(getString(R.string.firebase_pastBPM)).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String pastBPM = (dataSnapshot.getValue(String.class));
                    tvPastBPM.setText(pastBPM);
                    String[] arr = convertStringToArray(pastBPM);
                    double total = 0;
                    for (int j = 0; j < arr.length; j++) {
                        total += Integer.parseInt(arr[j]);
                    }
                    double avg = total / arr.length;
                    avgBPM.setText(String.format("%.2f", avg) + " BPM");
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    String pastBPM = (dataSnapshot.getValue(String.class));
                    tvPastBPM.setText(pastBPM);
                    String[] arr = convertStringToArray(pastBPM);
                    double total = 0;
                    for (int j = 0; j < arr.length; j++) {
                        total += Integer.parseInt(arr[j]);
                    }
                    double avg = total / arr.length;
                    avgBPM.setText(String.format("%.2f", avg) + " BPM");
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
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
