package com.github.kiolk.cowsandbulls.data.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.github.kiolk.cowsandbulls.domain.use_cases.SetDeviceToken;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //TODO implement logic for showing custom notification
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("MyLogs", s);
        new SetDeviceToken(new SetDeviceToken.Params(s), null).start();
    }
}
