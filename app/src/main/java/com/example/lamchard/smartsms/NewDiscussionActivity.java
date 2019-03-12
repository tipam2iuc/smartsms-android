package com.example.lamchard.smartsms;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_discussion);

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

                Message message = new Message("LUNDI 4 MARS 2019", Message.TypeMessage.LineStart);
                messageList.add(message);
                Message messager = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
                messageList.add(messager);
                Message message9 = new Message("HIER", Message.TypeMessage.LineStart);
                messageList.add(message9);
                Message messager8 = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
                messageList.add(messager8);
                Message message1 = new Message(editTextMessage.getText().toString(),true, Message.TypeMessage.Conversation);
                messageList.add(message1);
                Message messager1 = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
                messageList.add(messager1);
                Message message2 = new Message("AUJOURD'HUI", Message.TypeMessage.LineStart);
                messageList.add(message2);
                Message messager3 = new Message(editTextMessage.getText().toString(),true, Message.TypeMessage.Conversation);
                messageList.add(messager3);
                Message messager4 = new Message(editTextMessage.getText().toString(),false, Message.TypeMessage.Conversation);
                messageList.add(messager4);
                messageAdapter.notifyDataSetChanged();

                if(!isVisible()){
                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
                }
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
