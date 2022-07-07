package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    TextView titletv , currenttime , totaltime ;
    ImageView pauseplaybtn, nextbtn , previousbtn , musicbtn;
    SeekBar seekBar;
    ArrayList<AudioModel> songslist;
    AudioModel currentsong;
    int x = 0;
    MediaPlayer mediaPlayer = Mymediaplayer.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        titletv = findViewById(R.id.song_title);
        currenttime = findViewById(R.id.current_time);
        totaltime = findViewById(R.id.total_time);
        pauseplaybtn = findViewById(R.id.pause_play);
        nextbtn = findViewById(R.id.next);
        previousbtn = findViewById(R.id.previous);
        musicbtn = findViewById(R.id.music_icon_big);
        seekBar = findViewById(R.id.seek_bar);

        titletv.setSelected(true);
        songslist = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

        setResourcesWithmusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currenttime.setText(converttoMMSS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()){
                        pauseplaybtn.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                        musicbtn.setRotation(x++);
                    }
                    else{
                        pauseplaybtn.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                       musicbtn.setRotation(0);
                    }
                }
                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
                    mediaPlayer.seekTo(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    void setResourcesWithmusic(){
        currentsong = songslist.get(Mymediaplayer.currentIndex);
        titletv.setText(currentsong.getTitle());
        totaltime.setText(converttoMMSS(currentsong.getDuration()));

        pauseplaybtn.setOnClickListener(v-> pauseplay());
        nextbtn.setOnClickListener(v-> playnextmusic());
        previousbtn.setOnClickListener(v-> playpreviousmusic());

        playmusic();
    }

    private void playmusic(){

        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(currentsong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playnextmusic(){

        if(Mymediaplayer.currentIndex == songslist.size()-1)
            return;
        Mymediaplayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourcesWithmusic();
    }

    private void playpreviousmusic(){


        if(Mymediaplayer.currentIndex == 0)
            return;
        Mymediaplayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourcesWithmusic();
    }

    private void pauseplay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();

    }

    public static String converttoMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis)% TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis)% TimeUnit.MINUTES.toSeconds(1));
    }
}