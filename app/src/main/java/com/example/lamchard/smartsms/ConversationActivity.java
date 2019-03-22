package com.example.lamchard.smartsms;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lamchard.smartsms.Models.Permission;
import com.google.firebase.auth.FirebaseAuth;

public class ConversationActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.lamchard.smartsms.EXTRA_ID";
    public static final String EXTRA_Name =
            "com.example.lamchard.smartsms.EXTRA_Name";
    public static final String EXTRA_PhoneNumber =
            "com.example.lamchard.smartsms.EXTRA_PhoneNumber";
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 3;
    private static final String TAG = NewDiscussionActivity.class.getSimpleName();
    private String number;

    EditText editTextMessage_conversation;
    ImageButton imageButtonSendMessage_conversation;

    //tools
    Permission permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        permission = new Permission(this,this);

        imageButtonSendMessage_conversation = findViewById(R.id.imageButtonSendMessage_conversation);
        editTextMessage_conversation = findViewById(R.id.editTextMessage_conversation);

        toolbar = (Toolbar)findViewById(R.id.toolbar_conv);
        setSupportActionBar(toolbar);
        this.configureToolbar();

        Intent intent = getIntent();
        number = intent.getStringExtra(EXTRA_Name);
        toolbar.setTitle(number);

        imageButtonSendMessage_conversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editTextMessage_conversation.getText().toString())) {
                    permission.checkForSmsPermission(editTextMessage_conversation.getText().toString(),
                            number);
                }else {
                    Toast.makeText(ConversationActivity.this,
                            "veuillez specifier le destinataire et un message svp!!!",Toast.LENGTH_LONG).show();
                }
                // clean edittext
                editTextMessage_conversation.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.phone_call_id:
                permission.checkForPhonePermission(number);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void configureToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar_conv);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Check if permission is granted or not for the request.
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.CALL_PHONE)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    permission.checkForPhonePermission(editTextMessage_conversation.getText().toString());
                } else {
                    // Permission denied.
                    Log.d(TAG, "Failure to obtain permission!");
                    Toast.makeText(this,
                            "Failure to obtain permission!",
                            Toast.LENGTH_LONG).show();
                }
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase
                        (Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    permission.checkForSmsPermission(editTextMessage_conversation.getText().toString(),
                            number);
                } else {
                    // Permission denied.
                    Log.d(TAG, "failure_permission");
                    Toast.makeText(this,"failure_permission",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
