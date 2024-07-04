package com.example.spongebobvsjellyfish.Utilities;
import android.media.MediaPlayer;

import com.example.spongebobvsjellyfish.Logic.GameManager;
import com.example.spongebobvsjellyfish.Screen.MainActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundPlayer {


    private MainActivity context;
    private Executor executor;
    private MediaPlayer mediaPlayer;
    private MediaPlayer crash;
    public SoundPlayer(MainActivity context) {
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playSoundGame(int resID) {
        if (mediaPlayer == null)
            executor.execute(() -> {
                mediaPlayer = MediaPlayer.create(context, resID);
                mediaPlayer.setLooping(true);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
            });
    }
    public void playSoundCrash(int resID) {
        if (mediaPlayer == null)
            executor.execute(() -> {
                mediaPlayer = MediaPlayer.create(context, resID);
                mediaPlayer.setLooping(false);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
            });
    }

    public void stopSoundGame(int resID) {
        if (mediaPlayer != null){
            executor.execute(() ->{
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
    }
}