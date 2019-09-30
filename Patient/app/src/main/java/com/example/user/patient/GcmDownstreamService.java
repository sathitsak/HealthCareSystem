package com.example.user.patient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by user on 3/10/2016.
 */
public class GcmDownstreamService extends GcmListenerService {
    private static final String TAG = "GcmDownstreamService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        // TODO Do something here
        Log.d("aa", "Message Incoming");

    }
}
