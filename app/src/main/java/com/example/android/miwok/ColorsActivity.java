 package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

 public class ColorsActivity extends AppCompatActivity {

     private MediaPlayer mMediaPlayer;

     //listener gets triggered when the mediaplayer has completed playing the audio file
     private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
         @Override
         public void onCompletion(MediaPlayer mp) {
             releaseMediaPlayer();
         }
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        final ArrayList<Word> colors = new ArrayList<>();

        colors.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        colors.add(new Word("green", "chokokko", R.drawable.color_green, R.raw.color_green));
        colors.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
        colors.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
        colors.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        colors.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        colors.add(new Word("dusty yellow", "topiisa", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colors.add(new Word("mustard yellow", "chiwiita", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter adapter_colors = new WordAdapter(this, colors, R.color.category_colors);

        ListView listViewColors = (ListView) findViewById(R.id.list);
        listViewColors.setAdapter(adapter_colors);

        listViewColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word color = colors.get(position);

                mMediaPlayer = MediaPlayer.create(ColorsActivity.this, color.getAudioResourceId());
                mMediaPlayer.start();

                //Setup a listner on the mediaplayer, so that we can stop and release the
                //mediaplyer once the souund has finished playing
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });

    }

     @Override
     protected void onStop() {
         super.onStop();
         //When activity is stopped, release media player resources
         releaseMediaPlayer();
     }

     private void releaseMediaPlayer() {
         //If mediaplayer is not null, it maybe currently playing a sound
         if (mMediaPlayer != null) {
             // Regardless of the cuurent state of the player, release its resources
             //coz we no longer needs it
             mMediaPlayer.release();

             //set the mediaplayer back to null
             //easier way to tell that the mediaplayer is not config to play an audio file now
             mMediaPlayer = null;

         }
     }

 }