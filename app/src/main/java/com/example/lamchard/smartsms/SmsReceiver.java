package com.example.lamchard.smartsms;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.lamchard.smartsms.Models.Discussion;
import com.example.lamchard.smartsms.Models.SmsManagers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class SmsReceiver extends BroadcastReceiver {

    private SmsManagers smsManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        smsManager = new SmsManagers(context);

        Map<String, String> msg = RetrieveMessages(intent);

        if (msg == null) {
            Toast.makeText(context, "no receved message", Toast.LENGTH_SHORT).show();
        } else {


            for (String sender : msg.keySet()) {
                String message = msg.get(sender);

                smsManager.addSmsToRecevedBox(message, sender);
                Toast.makeText(context, sender + " : " + message, Toast.LENGTH_LONG).show();
                Log.i("INFORMATION", "SMS from " + sender + " Contenu: " + message);

                // update discussion adapter
                listOfDiscussion(new MainActivity());
                FragmentDiscussion.discussionAdapter.notifyDataSetChanged();
            }
        }
    }

    private static Map<String, String> RetrieveMessages(Intent intent) {
        Map<String, String> msg = null;
        SmsMessage[] msgs;
        Bundle bundle = intent.getExtras();

        if (bundle != null && bundle.containsKey("pdus")) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            if (pdus != null) {
                int nbrOfpdus = pdus.length;
                msg = new HashMap<String, String>(nbrOfpdus);
                msgs = new SmsMessage[nbrOfpdus];

                // There can be multiple SMS from multiple senders, there can be a maximum of nbrOfpdus different senders
                // However, send long SMS of same sender in one message
                for (int i = 0; i < nbrOfpdus; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    String originatinAddress = msgs[i].getOriginatingAddress();

                    // Check if index with number exists
                    if (!msg.containsKey(originatinAddress)) {
                        // Index with number doesn't exist
                        // Save string into associative array with sender number as index
                        msg.put(msgs[i].getOriginatingAddress(), msgs[i].getMessageBody());

                    } else {
                        // Number has been there, add content but consider that
                        // msg.get(originatinAddress) already contains sms:sndrNbr:previousparts of SMS,
                        // so just add the part of the current PDU
                        String previousparts = msg.get(originatinAddress);
                        String msgString = previousparts + msgs[i].getMessageBody();
                        msg.put(originatinAddress, msgString);
                    }
                }
            }
        }

        return msg;
    }

    private void listOfDiscussion(Activity context){

        List<Discussion> discussion = new ArrayList<>();
        try {
            Cursor cursor = context.getContentResolver()
                    .query(Telephony.Sms.Inbox.CONTENT_URI, new String[]{"address","body","date"}, null, null, "date DESC");

            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date")).toString();
                discussion.add(new Discussion(number, body, date,"22/22/22","1"));
            }
            cursor.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

}