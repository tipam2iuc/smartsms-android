package com.example.lamchard.smartsms;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class NewDiscussionActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);

        toolbar = (Toolbar)findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);

        this.configureToolbar();

    }

    private void configureToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar and Enable the Up button
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
