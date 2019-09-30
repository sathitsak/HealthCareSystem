package com.example.user.patient;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by user on 3/10/2016.
 */
public class GcmTokenRefreshService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GcmRegisterService.class);
        startService(intent);
    }
}
