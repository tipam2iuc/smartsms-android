package com.example.lamchard.smartsms.Models;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.lamchard.smartsms.FragmentDiscussion;
import com.example.lamchard.smartsms.MainActivity;
import com.example.lamchard.smartsms.NewDiscussionActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SmsManagers {
    private static Context context;
    public static List<Discussion> discussionList;
    private static final String TAG = NewDiscussionActivity.class.getSimpleName();

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
        String SENT_SMS_CONTENT_PROVIDER_URI_OLDER_API_19 = "content://sms/sent";
        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("date", System.currentTimeMillis());
        values.put("body", message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            context.getContentResolver().insert(Telephony.Sms.Sent.CONTENT_URI, values);
        else
            context.getContentResolver().insert(Uri.parse(SENT_SMS_CONTENT_PROVIDER_URI_OLDER_API_19), values);
    }

    public void callNumber(String number) {
        String phoneNumber = String.format("tel: %s",
                number);
        Toast.makeText(context,phoneNumber,Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));
        if (callIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(callIntent);
        } else {
            Toast.makeText(context,"l'appel a echouer",Toast.LENGTH_LONG).show();
        }
    }

    public void getSMSCOnversationlist(String number) {
        discussionList = new ArrayList<>();

        String searchQuery = "address like '%" + number + "%'";

        Cursor c = context.getContentResolver().query(Telephony.Sms.CONTENT_URI,null,searchQuery, null, "date desc");

        while(c.moveToNext()){
            String count = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
            String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
            String snippet = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
            discussionList.add(new Discussion(count, body, timeMillisToDate(snippet)));
        }
        c.close();
    }

    public void getSMSCOnversationlist() {
        discussionList = new ArrayList<>();

        String[] projections = new String[]{"DISTINCT address","body","date"};
        String selection = "address IS NOT NULL) GROUP BY (address";

        Cursor c = context.getContentResolver().query(Telephony.Sms.CONTENT_URI,null,selection, null, "date desc");


        while(c.moveToNext()){
            String count = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
            String body = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY));
            String snippet = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE));
            discussionList.add(new Discussion(count, body, timeMillisToDate(snippet)));
        }
        c.close();
    }

    private String timeMillisToDate(String timeMillis){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        Date date = new Date(Long.parseLong(timeMillis));
        return df.format(date);
    }

    public long getCurrentTimeMillis(Date date){

        return date.getTime();
    }

    public void SendMessage(String phoneNumber, String message) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(context, "SMS sent.",
                Toast.LENGTH_LONG).show();

//        String date = java.text.DateFormat.getDateTimeInstance().format(Calendar
//                .getInstance().getTime());
//        Message messageTime = new Message(date, Message.TypeMessage.LineStart);
//        messageList.add(messageTime);
//        Message message2 = new Message(editTextMessage.getText().toString(), false, Message.TypeMessage.Conversation);
//        messageList.add(message2);
//        messageAdapter.notifyDataSetChanged();

        // clean message
//        editTextMessage.setText("");

//        if (!isVisible()) {
//            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
//        }

        // store message
        addSmsToSenddBox(message,phoneNumber);
    }
}
