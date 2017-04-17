package com.example.android.miwok;

/**
 * Created by Juda on 16/04/2017.
 */

public class Word {

    private String english;
    private String greek;
    private int imageResourceId = NO_IMAGE_PROVIDED;
    private int audioResourceId;

    private static final int NO_IMAGE_PROVIDED = -1;


    public Word(String english, String greek, int audioResourceId) {
        this.greek = greek;
        this.english = english;
        this.audioResourceId = audioResourceId;
    }

    public Word(String english, String greek, int imageResourceId, int audioResourceId) {
        this.english = english;
        this.greek = greek;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
    }

    public String getEnglish() {
        return english;
    }

    public String getGreek() {
        return greek;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }
}
