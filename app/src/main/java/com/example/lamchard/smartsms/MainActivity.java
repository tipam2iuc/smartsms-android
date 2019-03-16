package com.example.lamchard.smartsms;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lamchard.smartsms.adapters.ViewPagerAdapter;
import com.example.lamchard.smartsms.models.Discussion;
import com.example.lamchard.smartsms.models.SmsManagers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private FloatingActionButton btn_addNewMessage;
    private Button button_dialog_call;
    private Button button_dialog_mesage;
    private ImageButton imageButtonSendMessage;

    //request permission
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    private static final String TAG = MainActivity.class.getSimpleName();


    //Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // lis of discussions
        getSMSCOnversationlist("658197933");


        //Initialisation des composants
        btn_addNewMessage = findViewById(R.id.floatingAdd);
        button_dialog_call = findViewById(R.id.dialog_btn_call);
        button_dialog_mesage = findViewById(R.id.dialog_btn_message);
        imageButtonSendMessage = findViewById(R.id.imageButtonSendMessage);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //init Firebase
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewpage_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Add fragments
        adapter.AddFragment(new FragmentDiscussion(), "");
        adapter.AddFragment(new FragmentDiffusion(), "");
        adapter.AddFragment(new FragmentContacts(), "");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_message);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_call);

        btn_addNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newActivity = new Intent(getApplicationContext(), NewDiscussionActivity.class);
                startActivity(newActivity);
            }
        });

        // les permissions
        //checkForSmsPermission();
        checkForPhonePermission();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.search_id);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id)
        {
            case R.id.settings_id:
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deconnection_id:
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginActivity);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
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
                    // Permission was granted.
                } else {
                    // Permission denied.
                    Log.d(TAG, "Failure to obtain permission!");
                    Toast.makeText(this,
                            "Failure to obtain permission!",
                            Toast.LENGTH_LONG).show();
                    // Disable the call button
                    //disableCallButton();
                }
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase
                    (Manifest.permission.SEND_SMS)
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted. Enable sms button.
                    //enableSmsButton();
                } else {
                    // Permission denied.
                    Log.d(TAG, "failure_permission");
                    Toast.makeText(this,"failure_permission",
                            Toast.LENGTH_LONG).show();
                    // Disable the sms button.
                    //disableSmsButton();
                }
            }
        }
    }

    private void checkForSmsPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //showContacts();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    private void checkForPhonePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
    }

    private void enableCallButton() {
        button_dialog_call.setEnabled(true);
    }

    private void enableSmsButton() {
        button_dialog_mesage.setEnabled(true);
        imageButtonSendMessage.setEnabled(true);
    }

    private void disableSmsButton() {
        button_dialog_mesage.setEnabled(false);
        imageButtonSendMessage.setEnabled(false);
    }

    private void disableCallButton() {
        button_dialog_call.setEnabled(false);
    }


    //
    private void listOfDiscussion(){

        FragmentDiscussion.discussions = new ArrayList<>();
        String varSearch = "693176070";
        String searchQuery = "address like '%" + varSearch + "%'";

        try {
            Cursor cursor = getContentResolver()
                    .query(Telephony.Sms.Inbox.CONTENT_URI, new String[]{"address","body","date"}, searchQuery, null, "date DESC");

            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString();

                // date formating
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                date = formatter.parse(date.toString()).toString();

                FragmentDiscussion.discussions.add(new Discussion(number, body, date));
            }
            cursor.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

    private void getSMSCOnversationlist(String number) {
        FragmentDiscussion.discussions = new ArrayList<>();
        String searchQuery = "address like '%" + number + "%'";
        //Uri SMS_INBOX = Uri.parse(Telephony.Sms.Conversations.CONTENT_URI.toString());
        Cursor c = getContentResolver().query(Telephony.Sms.CONTENT_URI,null,searchQuery, null, "date desc");


        while(c.moveToNext()){
            String count = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)).toString();
            String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY)).toString();
            String snippet = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE)).toString();
            FragmentDiscussion.discussions.add(new Discussion(count, body, SmsManagers.timeMillisToDate(snippet)));
        }
        c.close();
    }
}
