/*
    Designated Drivers
 */

package com.ceng319.lifelinesbreathalyzer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private ProgressBar progressBar;
    EditText emailEditText;
    EditText passwordEditText;
    TextView signup;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance();

        emailEditText = (EditText)findViewById(R.id.loginEmail);
        passwordEditText = (EditText)findViewById(R.id.loginPassword);
        signup = (TextView)findViewById(R.id.loginSignUp) ;
        login = (Button)findViewById(R.id.loginButton);
        progressBar = (ProgressBar)findViewById(R.id.loginProgressBar);


        signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                email = email.trim();
                password = password.trim();

                if (email.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage(R.string.login_error_message)
                            .setTitle(R.string.dialog_error)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    LoginTask login = new LoginTask();
                    login.execute(email, password);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private class LoginTask extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
            // shows progress bar
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        protected Void doInBackground(String... params) {
            // firebase signin task
            mFirebaseAuth.signInWithEmailAndPassword(params[0], params[1])
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(task.getException().getMessage())
                                        .setTitle(R.string.dialog_error)
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                progressBar.setVisibility(ProgressBar.INVISIBLE);
                            }
                        }
                    });
            return null;
        }

        protected void onProgressUpdate() { }

        protected void onPostExecute() {
            // Hide the progress bar
            progressBar.setVisibility(ProgressBar.INVISIBLE);
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

}

