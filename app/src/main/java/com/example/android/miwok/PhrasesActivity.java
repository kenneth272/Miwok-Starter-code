package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.android.miwok.R.id.family;
import static com.example.android.miwok.R.id.phrases;

public class PhrasesActivity extends AppCompatActivity {

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

        final ArrayList<Word> phrases = new ArrayList<>();

        phrases.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I'm feeling good", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I'm coming", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I'm coming", "әәnәm", R.raw.phrase_im_coming));
        phrases.add(new Word("Let's Go", "yoowutis", R.raw.phrase_lets_go));
        phrases.add(new Word("Come Here", "әnni nem", R.raw.phrase_come_here));

        WordAdapter adapter_phrases = new WordAdapter(this, phrases, R.color.category_phrases);

        ListView listViewColors = (ListView) findViewById(R.id.list);
        listViewColors.setAdapter(adapter_phrases);
        listViewColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word phrase = phrases.get(position);

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

                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, phrase.getAudioResourceId());
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
