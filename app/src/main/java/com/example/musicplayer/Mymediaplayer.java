package com.example.musicplayer;

import android.media.MediaPlayer;

public class Mymediaplayer {
    static MediaPlayer Instance;
    public static MediaPlayer getInstance(){
        if(Instance == null){
            Instance = new MediaPlayer();

        }
        return Instance;

    }
    public static int currentIndex = -1;
}
