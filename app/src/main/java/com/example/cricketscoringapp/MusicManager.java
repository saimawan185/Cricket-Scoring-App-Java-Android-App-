package com.example.cricketscoringapp;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    public static void startMusic(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.audio);
            mediaPlayer.setLooping(true); // Ensure continuous play
        }
        if (!isPlaying) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public static void releaseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    public static boolean isMusicPlaying() {
        return isPlaying;
    }
}

