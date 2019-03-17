package com.example.lamchard.smartsms.models;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.lamchard.smartsms.NewDiscussionActivity;

public class Permission {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 3;
    private static final String TAG = NewDiscussionActivity.class.getSimpleName();
    private static Activity activity;
    private static Context context;
    SmsManagers manager;
    
    public  Permission(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        manager = new SmsManagers(context);
    }

    public void checkForSmsPermission(String message, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "permission_not_granted");
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            //enableSmsButton();
            manager.SendMessage(phoneNumber, message);
        }
    }

    public void checkForPhonePermission(String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "PERMISSION NOT GRANTED!");
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Permission already granted. Enable the call button.
            //enableCallButton();
            manager.callNumber(phoneNumber);
        }
    }
}
