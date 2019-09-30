package com.example.user.patient;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class GcmRegisterService extends IntentService  {
    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    String id_p;

    public GcmRegisterService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        id_p = intent.getStringExtra("patient_id");

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            sendRegistrationToServer(token);

            subscribeTopics(token);

            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, true).apply();

        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);

            sharedPreferences.edit().putBoolean(SENT_TOKEN_TO_SERVER, false).apply();
        }

        Intent registrationComplete = new Intent(REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
       /** String url="http://192.168.56.1/patient/savetoken.php?patient_id="+id_p+"&token="+token;
        Log.d("aa", "bb" + url);

        SaveTokenTask task=new SaveTokenTask();
        task.execute(url);
*/
    /*    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token",token);
*/

Log.d("rr","qq"+id_p);
        String url="http://104.131.23.206/patient/savetoken.php?patient_id="+id_p+"&token="+token;

        SaveTokenTask task=new SaveTokenTask();
        task.execute(url);

      //  Log.e(TAG, "Token : " + token);

    }

    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }



}
