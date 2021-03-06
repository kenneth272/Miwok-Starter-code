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

import static com.example.android.miwok.R.id.colors;


public class FamilyActivity extends AppCompatActivity {

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

        final ArrayList<Word> family = new ArrayList<>();

        family.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        family.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        family.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        family.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        family.add(new Word("older sister", "tete", R.drawable.family_older_sister, R.raw.family_older_sister));
        family.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        family.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        family.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter adapter_family = new WordAdapter(this, family, R.color.category_family);

        ListView listViewFamily = (ListView) findViewById(R.id.list);
        listViewFamily.setAdapter(adapter_family);

        listViewFamily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word famil = family.get(position);

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

                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, famil.getAudioResourceId());
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