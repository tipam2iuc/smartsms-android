package com.example.lamchard.smartsms;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class ConversationActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.lamchard.smartsms.EXTRA_ID";
    public static final String EXTRA_Name =
            "com.example.lamchard.smartsms.EXTRA_Name";
    public static final String EXTRA_PhoneNumber =
            "com.example.lamchard.smartsms.EXTRA_PhoneNumber";
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        toolbar = (Toolbar)findViewById(R.id.toolbar_conv);
        setSupportActionBar(toolbar);
        this.configureToolbar();

        Intent intent = getIntent();
        toolbar.setTitle(intent.getStringExtra(EXTRA_Name));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conv, menu);
        return true;
    }

    private void configureToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar_conv);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
