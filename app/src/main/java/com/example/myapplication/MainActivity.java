package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerControlView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button, testButton;
    ExoPlayer player;
    PlayerControlView playerControlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("Package: ", getPackageName());
        Log.e("Activity:", " Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.start_service);
        testButton = findViewById(R.id.test_music);
        playerControlView = findViewById(R.id.player_view);

        //test button
/*
        testButton.setOnClickListener(view -> {
            Uri[] uri = new Uri[3];
            uri[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_song_0);
            uri[1] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_song_1);
            uri[2] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_song_2);
            for(int i = 0; i<3; i++){
                Log.e("Uri", "" + uri[i]);
            }
            try {
                mediaPlayer.setDataSource(this, uri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        });
*/

        //Set up exoplayer
        player = new ExoPlayer.Builder(this).build();
        prepareSongPlaylist(player);
        playerControlView.setPlayer(player);
        //Pass player to Receiver
        Receiver.getPlayer(player);
        Intent intent = new Intent(this, ExampleService.class);
        button.setOnClickListener(view -> {
            player.setPlayWhenReady(true);
            Log.d("Service: ", "Started");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        });

        testButton.setOnClickListener(view -> {
            player.pause();
            stopService(intent);
        });

    }

    public void prepareSongPlaylist(ExoPlayer player) {
        Uri[] uris = new Uri[4];
        uris[0] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_song_0);
        uris[1] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_song_1);
        uris[2] = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_song_2);
        MediaItem[] items = new MediaItem[4];
        for (int i = 0; i < 3; i++) {
            items[i] = MediaItem.fromUri(uris[i]);
            player.addMediaItem(items[i]);
        }
/*
        Receiver.getSongPlaylist(items);
*/
        player.prepare();
    }
}