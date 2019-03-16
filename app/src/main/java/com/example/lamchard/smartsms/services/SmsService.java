package com.example.lamchard.smartsms.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.example.lamchard.smartsms.SmsReceiver;

public final class SmsService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
