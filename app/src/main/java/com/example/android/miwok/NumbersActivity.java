package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.CamcorderProfile.get;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListner =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                        focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ) {
                        //AUDIOFOCUS_LOSS_TRANSIENT - lost audio focus for short amt of time
                        //AUDIOFOCUS_LOSS_TRANSENT_CAN_DUCK - app allows to play sound at a lower vol

                        //Pause playback and reset player to the start of the file.
                        //Play from the beginning when we resume playback
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);

                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //AUDIOFOCUS_GAIN - regain focus and can resume playback
                        mMediaPlayer.start();

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                       //AUDIOFOCUS_LOSS - lost audio focus and stop playback and cleanu record
                        releaseMediaPlayer();
                    }
                }
    };

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

        //Create and setup the AudioManager to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter adapter =
                new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                //Release Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                //Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListner,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //we have audio focus now

                    //Create & setup the mediaplayer for the audio resources
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
                    mMediaPlayer.start();

                    //Setup a listner on the mediaplayer, so that we can stop and release the
                    //mediaplyer once the souund has finished playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //When activity is stopped, release media player resources
        releaseMediaPlayer();
    }

        //Clean up the media player by releasing its resources

    private void releaseMediaPlayer() {
        //If mediaplayer is not null, it maybe currently playing a sound
        if (mMediaPlayer != null) {
            // Regardless of the cuurent state of the player, release its resources
            //coz we no longer needs it
            mMediaPlayer.release();

            //set the mediaplayer back to null
            //easier way to tell that the mediaplayer is not config to play an audio file now
            mMediaPlayer = null;

            //Regardsless of whether or not we were granted audio focus, abandon it.
            //Unregistered the audioFocusChangeListner so we dont get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListner);

        }
    }

    }


