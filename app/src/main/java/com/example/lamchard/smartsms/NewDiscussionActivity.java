package com.example.lamchard.smartsms;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lamchard.smartsms.Adapters.MessageAdapter;
import com.example.lamchard.smartsms.Models.Message;

import java.util.ArrayList;
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

                Message message = new Message(editTextMessage.getText().toString(),true);
                messageList.add(message);
                Message messager = new Message(editTextMessage.getText().toString(),false);
                messageList.add(messager);
                Message message1 = new Message(editTextMessage.getText().toString(),false);
                messageList.add(message1);
                Message messager1 = new Message(editTextMessage.getText().toString(),false);
                messageList.add(messager1);
                Message message2 = new Message(editTextMessage.getText().toString(),true);
                messageList.add(message2);
                Message messager3 = new Message(editTextMessage.getText().toString(),true);
                messageList.add(messager3);
                Message messager4 = new Message(editTextMessage.getText().toString(),false);
                messageList.add(messager4);
                messageAdapter.notifyDataSetChanged();
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
}
