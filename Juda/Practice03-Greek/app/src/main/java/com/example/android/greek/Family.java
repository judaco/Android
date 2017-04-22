package com.example.android.greek;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.greek.R;
import com.example.android.greek.Word;
import com.example.android.greek.WordAdapter;

import java.util.ArrayList;

public class Family extends AppCompatActivity {

    private MediaPlayer player;

    private AudioManager audioManager;

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                player.pause();
                player.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                player.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("father", "πατέρας", R.drawable.family_father, R.raw.father));
        words.add(new Word("mother", "μητέρα", R.drawable.family_mother, R.raw.mother));
        words.add(new Word("son", "γιος", R.drawable.family_son, R.raw.son));
        words.add(new Word("daughter", "κόρη", R.drawable.family_daughter, R.raw.daughter));
        words.add(new Word("grandmother", "γιαγιά", R.drawable.family_grandmother, R.raw.grandmother));
        words.add(new Word("grandfather", "παππούς", R.drawable.family_grandfather, R.raw.grandfather));
        words.add(new Word("sister", "αδελφή", R.drawable.family_younger_sister, R.raw.sister));
        words.add(new Word("brother", "αδελφός", R.drawable.family_younger_brother, R.raw.brother));
        words.add(new Word("uncle", "θείος", R.drawable.family_older_brother, R.raw.uncle));
        words.add(new Word("aunt", "θεία", R.drawable.family_older_sister, R.raw.aunt));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = words.get(position);

                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    player = MediaPlayer.create(Family.this, word.getAudioResourceId());
                    player.start();
                    player.setOnCompletionListener(completionListener);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (player != null) {
            player.release();
            player = null;
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}