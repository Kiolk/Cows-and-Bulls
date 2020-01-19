package com.github.kiolk.cowsandbulls.data.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.github.kiolk.cowsandbulls.R;
import com.github.kiolk.cowsandbulls.domain.use_cases.SetDeviceToken;
import com.github.kiolk.cowsandbulls.ui.screens.GameActivity;
import com.github.kiolk.cowsandbulls.utils.StringUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createNotification(remoteMessage.getData());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        new SetDeviceToken(new SetDeviceToken.Params(s), null).start();
    }

    private void createNotification(Map<String, String> data) {
        String login = data.get("login");
        String time = data.get("time");
        String moves = data.get("moves");
        String position = data.get("position");

        if (time == null) {
            time = "";
        }

        Intent notificationIntent = new Intent(this, GameActivity.class);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int notificationId = new Random().nextInt();
        PendingIntent intent = PendingIntent.getActivity(this, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_bull)
                .setContentTitle(getBaseContext().getResources().getString(R.string.notification_title, position))
                .setContentText(getBaseContext().getResources().getString(R.string.notification_body, login, StringUtils.getTime(Integer.parseInt(time)), moves))
                .setAutoCancel(true)
                .setGroup(String.valueOf(notificationId))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(alarmSound)
                .setVibrate(new long[]{0, 500, 0, 500})
                .setContentIntent(intent);

        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (mNotificationManager != null) {
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
    }
}
