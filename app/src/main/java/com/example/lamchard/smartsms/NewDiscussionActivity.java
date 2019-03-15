package com.example.lamchard.smartsms;

import android.Manifest;
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

import com.example.lamchard.smartsms.adapters.MessageAdapter;
import com.example.lamchard.smartsms.models.Message;
import com.example.lamchard.smartsms.models.SmsManagers;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);

        toolbar = (Toolbar)findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        this.configureToolbar();

        mdestinataire = findViewById(R.id.editText_nameDestinataire);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageButtonContact = findViewById(R.id.imageButton_contact);
        imageButtonSendMessafe = findViewById(R.id.imageButtonSendMessage);
        recyclerView = findViewById(R.id.recyclerView);

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
                    checkForSmsPermission();
                }else {
                    Toast.makeText(NewDiscussionActivity.this,"veuillez specifier le destinataire et un message svp!!!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
//imageButtonSendMessafe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Message message = new Message("LUNDI 4 MARS 2019", Message.TypeMessage.LineStart);
//                messageList.add(message);
//                Message messager = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
//                messageList.add(messager);
//                Message message9 = new Message("HIER", Message.TypeMessage.Debut);
//                messageList.add(message9);
//                Message messager8 = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
//                messageList.add(messager8);
//                Message message1 = new Message(editTextMessage.getText().toString(),true, Message.TypeMessage.Conversation);
//                messageList.add(message1);
//                Message messager1 = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
//                messageList.add(messager1);
//                Message message2 = new Message("AUJOURD'HUI", Message.TypeMessage.Debut);
//                messageList.add(message2);
//                Message messager3 = new Message(editTextMessage.getText().toString(),true, Message.TypeMessage.Conversation);
//                messageList.add(messager3);
//                Message messager4 = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
//                messageList.add(messager4);
//                messageAdapter.notifyDataSetChanged();
//
//                if(!isVisible()){
//                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
//                }
//            }
//        });

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
                    callNumber();
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
                    Toast.makeText(this,"permission granted",
                            Toast.LENGTH_LONG).show();
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
        // Get a support ActionBar corresponding to this toolbar and Enable the Up button
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

    private void SendMessage(String phoneNumber, String message) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.",
                Toast.LENGTH_LONG).show();

        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar
                .getInstance().getTime());
        Message messageTime = new Message(date, Message.TypeMessage.LineStart);
        messageList.add(messageTime);
        Message message2 = new Message(editTextMessage.getText().toString(), false, Message.TypeMessage.Conversation);
        messageList.add(message2);
        messageAdapter.notifyDataSetChanged();

        // clean message
        editTextMessage.setText("");

        if (!isVisible()) {
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        }

        // store message
        SmsManagers manager = new SmsManagers(this);
        manager.addSmsToSenddBox(editTextMessage.getText().toString(),mdestinataire.getText().toString());
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "permission_not_granted");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            //enableSmsButton();
            SendMessage(mdestinataire.getText().toString(), editTextMessage
                    .getText().toString());
        }
    }

    public void callNumber() {

        String phoneNumber = String.format("tel: %s",
                mdestinataire.getText().toString());
        // Log the concatenated phone number for dialing.
        Log.d(TAG, "Dialing : " + phoneNumber);
        Toast.makeText(this,
                "Dialing : " + phoneNumber,
                Toast.LENGTH_LONG).show();
        // Create the intent.
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        // Set the data for the intent as the phone number.
        callIntent.setData(Uri.parse(phoneNumber));
        // If package resolves to an app, send intent.
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_CALL Intent.");
        }
    }

    private void checkForPhonePermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "PERMISSION NOT GRANTED!");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Permission already granted. Enable the call button.
            //enableCallButton();
            callNumber();
        }
    }
}
