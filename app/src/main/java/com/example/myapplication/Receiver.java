package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;

public class Receiver extends BroadcastReceiver {

    static ExoPlayer player;
    static MediaItem[] items;

    public static void getPlayer(ExoPlayer player) {
        Receiver.player = player;
    }

    public static void getSongPlaylist(MediaItem[] items) {
        Receiver.items = items;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("Play/Pause");
        switch (action) {
            case ExampleService.PLAY_PAUSE:
                if (player.isPlaying()) {
                    player.setPlayWhenReady(false);
                    player.getPlaybackState();
                    Log.e("Player: ", "Pause");
                } else {
                    player.setPlayWhenReady(true);
                    player.getPlaybackState();
                    Log.e("Player: ", "Continue");
                }
                break;
            case ExampleService.SKIP_NEXT:
                if (player.hasNextMediaItem()) {
                    int indexNext = player.getCurrentMediaItemIndex() + 1;
                    player.setMediaItem(player.getMediaItemAt(indexNext));
                    player.setPlayWhenReady(true);
                    player.getPlaybackState();
                    Log.e("Player: ", "Skip to next song successfully!");
                } else {
                    Log.e("Player: ", "0 next song!");
                }
                break;
            case ExampleService.SKIP_PREVIOUS:
                if (player.hasPreviousMediaItem()) {
                    int indexPrevious = player.getCurrentMediaItemIndex() - 1;
                    player.setMediaItem(player.getMediaItemAt(indexPrevious));
                    player.setPlayWhenReady(true);
                    player.getPlaybackState();
                    Log.e("Player: ", "Skip to next song successfully!");
                }
                break;
        }
    }
}
