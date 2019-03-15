package com.example.lamchard.smartsms.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import com.example.lamchard.smartsms.FragmentDiscussion;

import java.util.ArrayList;
import java.util.List;

public class SmsManagers {
    private static Context context;
    public static List<Discussion> discussionList;

    public  SmsManagers(Context context) {
        this.context = context;
    }

    public void addSmsToRecevedBox(String message, String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("date", System.currentTimeMillis());
        values.put("body", message);
        context.getContentResolver().insert(Uri.parse("content://sms/inbox"), values);
    }

    public void addSmsToSenddBox(String message, String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("date", System.currentTimeMillis());
        values.put("body", message);
        context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
    }

    public void callNumber(String phoneNumber) {

        // Log the concatenated phone number for dialing.
        Toast.makeText(context,
                "Dialing : " + phoneNumber,
                Toast.LENGTH_LONG).show();
        // Create the intent.
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        // Set the data for the intent as the phone number.
        callIntent.setData(Uri.parse(phoneNumber));
        // If package resolves to an app, send intent.
        if (callIntent.resolveActivity(context.getPackageManager()) != null)
            context.startActivity(callIntent);
    }

    public static void testSimulationDisc(){

        FragmentDiscussion.discussions = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver()
                    .query(Telephony.Sms.Inbox.CONTENT_URI, new String[]{"address","body","date"}, null, null, "date DESC");

            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString();
                FragmentDiscussion.discussions.add(new Discussion(number, body, date));
            }
            cursor.close();
        }catch (Exception e){
            e.getMessage();
        }
    }
}
