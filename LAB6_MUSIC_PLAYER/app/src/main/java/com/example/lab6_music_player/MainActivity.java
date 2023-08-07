package com.example.lab6_music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Handler myHandler = new Handler();
    MediaPlayer music[] = new MediaPlayer[5];
     int forwardTime = 5000;
     int backwardTime = 5000;
    int[] songs = {R.raw.baby,R.raw.arikil,R.raw.unnikale,R.raw.nee_en,R.raw.aayiram};
    String[] song_names={"Baby Calm Down","Arikil Nee Undayirunenkil","Unnikale oru Kadha","Nee en Sarga","Aayiram Kannumayi"};
    int[] images={R.drawable.music_cover1,R.drawable.music_cover2,R.drawable.music_cover3,R.drawable.music_cover4,R.drawable.music_cover5};
    TextView txt,start_time,final_time;
    double startTime,finalTime;
    ImageView img;
    ImageButton imgBtn,stopBtn,nextBtn,previousBtn,forwardBtn,previousSongBtn;
    Boolean playPause = false;
    SeekBar seekBar;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=0;
        for(int j=0;j< songs.length;j++){
            music[j]=MediaPlayer.create(this,songs[j]);
        }

        img = (ImageView) findViewById(R.id.imageView);
        txt = (TextView) findViewById(R.id.songName);
        imgBtn =(ImageButton) findViewById(R.id.play);
        stopBtn = (ImageButton) findViewById(R.id.stop);
        nextBtn = (ImageButton) findViewById(R.id.next);
        previousSongBtn = (ImageButton) findViewById(R.id.previous);
        forwardBtn=(ImageButton)findViewById(R.id.forward);
        previousBtn=(ImageButton)findViewById(R.id.rewind);

        seekBar =(SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        start_time =(TextView)findViewById(R.id.startTime);
        final_time=(TextView)findViewById(R.id.finalTime);
        startTime=music[i].getCurrentPosition();
        finalTime=music[i].getDuration();

        final_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        start_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );

        txt.setText(song_names[i]);

        seekBar.setProgress((int)startTime);
        myHandler.postDelayed(UpdateSongTime,100);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSong(v);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStong(v);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong(v);
            }
        });

        previousSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSong(v);
            }
        });

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    music[i].seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped forward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    music[i].seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(),"You have Jumped backward 5 seconds",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        };



    Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = music[i].getCurrentPosition();
            if(startTime==finalTime)
            {

            }
            start_time.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int)startTime/1000);
            myHandler.postDelayed(this, 100);
        }
    };
    

    public void playSong(View view){
        if(!playPause){
            music[i].start();
            imgBtn.setImageResource(R.drawable.pause);
            playPause=true;
        }
        else{
            music[i].pause();
            imgBtn.setImageResource(R.drawable.play);
            playPause=false;
        }
        startTime=music[i].getCurrentPosition();
        finalTime=music[i].getDuration();
        final_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        start_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );
    }

    public void stopStong(View view){
      music[i].stop();
      music[i]= MediaPlayer.create(this,songs[i]);
      imgBtn.setImageResource(R.drawable.play);
      playPause=false;
        startTime=music[i].getCurrentPosition();
        finalTime=music[i].getDuration();
        final_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        start_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );
    }

    public void previousSong(View view){
        music[i].stop();
        music[i]= MediaPlayer.create(this,songs[i]);
        if(i==0){
            i = songs.length-1;
        }
        else{
            i--;
        }
        img.setImageResource(images[i]);
        txt.setText(song_names[i]);
        imgBtn.setImageResource(R.drawable.pause);
        playPause=true;
        music[i].start();
        startTime=music[i].getCurrentPosition();
        finalTime=music[i].getDuration();
        final_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        start_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );
    }

    public void nextSong(View view){
        music[i].stop();
        music[i]=MediaPlayer.create(this,songs[i]);
        if(i==songs.length-1){
            i=0;
        }
        else{
            i++;
        }
        img.setImageResource(images[i]);
        txt.setText(song_names[i]);
        imgBtn.setImageResource(R.drawable.pause);
        playPause=true;
        music[i].start();
        startTime=music[i].getCurrentPosition();
        finalTime=music[i].getDuration();
        final_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))
        );

        start_time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
        );
    }

}