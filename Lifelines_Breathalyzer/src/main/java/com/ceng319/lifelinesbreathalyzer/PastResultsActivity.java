package com.ceng319.lifelinesbreathalyzer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class PastResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_results);

        TextView textScrollable1 = (TextView)findViewById(R.id.PastBAClevels);
        TextView textScrollable2 = (TextView)findViewById(R.id.PastBPMLevels);
        //This is to enable scrolling on scrollview
        textScrollable1.setMovementMethod(new ScrollingMovementMethod());
        textScrollable2.setMovementMethod(new ScrollingMovementMethod());
    }
}
