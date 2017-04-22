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

public class Colors extends AppCompatActivity {

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
        words.add(new Word("red", "κόκκινο", R.drawable.color_red, R.raw.red));
        words.add(new Word("green", "πράσινο", R.drawable.color_green, R.raw.green));
        words.add(new Word("brown", "καφέ", R.drawable.color_brown, R.raw.brown));
        words.add(new Word("black", "μαύρο", R.drawable.color_black, R.raw.black));
        words.add(new Word("white", "άσπρο", R.drawable.color_white, R.raw.white));
        words.add(new Word("yellow", "κίτρινο", R.drawable.color_mustard_yellow, R.raw.yellow));
        words.add(new Word("blue", "μπλε", R.drawable.color_white, R.raw.blue));
        words.add(new Word("pink", "ροζ", R.drawable.color_dusty_yellow, R.raw.pink));
        words.add(new Word("purple", "μωβ", R.drawable.color_white, R.raw.purple));
        words.add(new Word("gray", "γκρί", R.drawable.color_gray, R.raw.gray));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);
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
                    player = MediaPlayer.create(Colors.this, word.getAudioResourceId());
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