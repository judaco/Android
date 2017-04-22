package com.example.juda.practice03_greeksmalldictionary;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

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

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

            audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

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

            WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);
            ListView listView = (ListView) rootView.findViewById(R.id.list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    releaseMediaPlayer();
                    Word word = words.get(position);

                    int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        player = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                        player.start();
                        player.setOnCompletionListener(completionListener);
                    }
                }
            });

        return rootView;
    }

    @Override
    public void onStop() {
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
