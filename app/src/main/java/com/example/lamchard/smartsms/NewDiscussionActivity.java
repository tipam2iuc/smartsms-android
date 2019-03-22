package com.example.lamchard.smartsms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lamchard.smartsms.Adapters.MessageAdapter;
import com.example.lamchard.smartsms.Models.Message;
import com.example.lamchard.smartsms.Models.Permission;
import com.example.lamchard.smartsms.Models.SmsManagers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewDiscussionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ImageButton imageButtonContact, imageButtonSendMessafe;
    private EditText mdestinataire, editTextMessage;
    private String number;
    private String name;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    static final int PICK_CONTACT_REQUEST = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 3;
    private static final String TAG = NewDiscussionActivity.class.getSimpleName();

    // tools
    SmsManagers manager;
    private Permission permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);

        manager = new SmsManagers(this);
        permission = new Permission(this,this);

        toolbar = (Toolbar)findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        this.configureToolbar();

        mdestinataire = findViewById(R.id.editText_nameDestinataire);
        editTextMessage = findViewById(R.id.editTextMessage_conversation);
        imageButtonContact = findViewById(R.id.imageButton_contact);
        imageButtonSendMessafe = findViewById(R.id.imageButtonSendMessage_conversation);
        recyclerView = findViewById(R.id.recyclerView_conversation);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(messageAdapter);

        imageButtonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContact();
            }
        });

        imageButtonSendMessafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mdestinataire.getText().toString()) &&
                        !TextUtils.isEmpty(editTextMessage.getText().toString())) {
                    permission.checkForSmsPermission(editTextMessage.getText().toString(),
                            mdestinataire.getText().toString());
                }else {
                    Toast.makeText(NewDiscussionActivity.this,
                            "veuillez specifier le destinataire et un message svp!!!",Toast.LENGTH_LONG).show();
                }
                // clean edittext
                editTextMessage.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_CONTACT_REQUEST){

            if(resultCode == RESULT_OK){

                Uri contactUri = data.getData();

                String[] projection = {
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };

                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null,null,null);
                cursor.moveToFirst();

                int column_number = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                int column_name = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

                number = cursor.getString(column_number);
                name = cursor.getString(column_name);

                mdestinataire.setText(name + ": " + number);

            }

        }
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
                    permission.checkForPhonePermission(mdestinataire.getText().toString());
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
                    permission.checkForSmsPermission(editTextMessage.getText().toString(),
                            mdestinataire.getText().toString());
                } else {
                    // Permission denied.
                    Log.d(TAG, "failure_permission");
                    Toast.makeText(this,"failure_permission",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_disc, menu);
        return true;
    }

    private void configureToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void getContact() {

        Intent contactIntent = new Intent(Intent.ACTION_PICK,Uri.parse("content://contacts"));
        contactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(contactIntent, PICK_CONTACT_REQUEST);
    }

    public boolean isVisible(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        int positionOfLastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = recyclerView.getAdapter().getItemCount();
        return (positionOfLastVisible>=itemCount);
    }

}
