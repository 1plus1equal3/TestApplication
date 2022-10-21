package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button, testButton;
    ExoPlayer player;
    PlayerControlView playerControlView;
    MediaPlayer mediaPlayer = new MediaPlayer();

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
        testButton.setOnClickListener(view -> {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test_song_0);
            Log.e("Uri: ", String.valueOf(uri));
            try {
                mediaPlayer.setDataSource(this, uri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        });

        //Set up exoplayer
        player = new ExoPlayer.Builder(this).build();
        prepareSongPlaylist(player);
        playerControlView.setPlayer(player);
        //Pass player to Receiver
        Receiver.getPlayer(player);
        Intent intent = new Intent(this, ExampleService.class);
        button.setOnClickListener(view -> {
            player.play();
            player.setPlayWhenReady(true);
            player.getPlaybackState();
            Log.d("Service: ", "Started");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        });

    }

    public void prepareSongPlaylist(ExoPlayer player) {
        Uri[] uris = new Uri[4];
        MediaItem[] items = new MediaItem[4];
        for (int i = 0; i < 4; i++) {
            String songPath = "android.resources://" + getPackageName() + "/" + R.raw.test_song_0;
            Log.e("Path: ", songPath);
            uris[i] = Uri.parse(songPath);
            items[i] = MediaItem.fromUri(uris[i]);
            player.addMediaItem(items[i]);
        }
/*
        Receiver.getSongPlaylist(items);
*/
        player.prepare();
    }
}