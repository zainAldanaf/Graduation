package com.example.palliativecareguidelines.Pationts;

import android.annotation.SuppressLint;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.palliativecareguidelines.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        getFirebaseMessage(message.getNotification().getTitle(),message.getNotification().getBody());
    }

    @SuppressLint("MissingPermission")
    private void getFirebaseMessage(String title, String Message) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"myfirebaseChaneel").setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle(title).setContentText(Message).setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(101,builder.build());
    }
}

