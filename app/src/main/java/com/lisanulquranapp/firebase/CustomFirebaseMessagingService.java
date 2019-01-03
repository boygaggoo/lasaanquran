package com.lisanulquranapp.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

/**
 * Created by ZeeZee on 11/5/2017.
 */

public class CustomFirebaseMessagingService extends FirebaseMessagingService
{
    
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        String completeMessage = remoteMessage.getData().get("message");
        showNotification(completeMessage.split(":")[1], completeMessage.split(":")[0]);

    }
    
    private void showNotification(String title, String message)
    {
        /*int notificationID = (int) (Calendar.getInstance().getTimeInMillis()/1000);
    
        Intent i = new Intent(this, SplashActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri)
                .setChannelId(Constants.CHANNEL_ID)
                .setGroup(Constants.NOTOFICATION_GROUP)
                .setContentIntent(pendingIntent);
    
        NotificationCompat.Builder summeryBuilder = new NotificationCompat.Builder(this);
        summeryBuilder.setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(soundUri)
                .setChannelId(Constants.CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setGroup(Constants.NOTOFICATION_GROUP)
                .setGroupSummary(true);
    
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID, "fast-bbq-waiter", importance);
            channel.setDescription("fast-bbq-waiter-app");
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(notificationID, builder.build());
        notificationManager.notify(Constants.NOTIFICATION_ID, summeryBuilder.build());*/
    }
}
