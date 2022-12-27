package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ExampleService extends Service {

    public static final String PLAY_PAUSE = "play_or_pause";
    public static final String SKIP_PREVIOUS = "skip_to previous";
    public static final String SKIP_NEXT = "skip_to next";


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Start MainActivity on click of notification
        Intent ActivityIntent = new Intent(this, MainActivity.class);
        ActivityIntent.setAction(Intent.ACTION_MAIN);
        ActivityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ActivityIntent, PendingIntent.FLAG_IMMUTABLE);
        //Play/Pause song from notification Action Button
        Intent PPB_intent = new Intent(this, Receiver.class);
        PPB_intent.putExtra("Play/Pause", PLAY_PAUSE);
        PPB_intent.setAction(PLAY_PAUSE);
        PendingIntent PPB_pendingIntent = PendingIntent.getBroadcast(this, 0, PPB_intent, PendingIntent.FLAG_IMMUTABLE);

        //Skip to previous song from notification Action Button
        Intent SPB_intent = new Intent(this, Receiver.class);
        SPB_intent.putExtra("SkipPrevious", SKIP_PREVIOUS);
        SPB_intent.setAction(SKIP_PREVIOUS);
        PendingIntent SPB_pendingIntent = PendingIntent.getBroadcast(this, 0, SPB_intent, PendingIntent.FLAG_IMMUTABLE);

        //Skip to next song from notification Action Button
        Intent SNB_intent = new Intent(this, Receiver.class);
        SNB_intent.putExtra("SkipNext", SKIP_NEXT);
        SNB_intent.setAction(SKIP_NEXT);
        PendingIntent SNB_pendingIntent = PendingIntent.getBroadcast(this, 0, SNB_intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_music_notification)
                .setContentTitle("Music Name")
                .setContentText("Music description")
                .setContentIntent(pendingIntent)
                .setOngoing(false)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Add an Action Button for Skip Previous
        builder.addAction(R.drawable.ic_music_skip_previous_btn, "SkipPrevious", SPB_pendingIntent);

        //Add an Action Button for Play/Pause
        builder.addAction(R.drawable.ic_music_play_pause_btn, "PlayorPause", PPB_pendingIntent);

        //Add an Action Button for Skip Next
        builder.addAction(R.drawable.ic_music_skip_next_btn, "SkipNext", SNB_pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
        createNotificationChannel(managerCompat);

        startForeground(1, builder.build());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(false);
        stopSelf();
        super.onDestroy();
    }


    public void createNotificationChannel(NotificationManagerCompat managerCompat) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("1", "Channel", NotificationManager.IMPORTANCE_DEFAULT);
            managerCompat.createNotificationChannel(channel);
        }
    }
}
