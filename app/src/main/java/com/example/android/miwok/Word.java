package com.example.android.miwok;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId;

    public Word(String defaultTransaltion, String MiwokTranslation) {
        this.mDefaultTranslation = defaultTransaltion;
        this.mMiwokTranslation = MiwokTranslation;
    }

    public Word(String defaultTransaltion, String MiwokTranslation, int ImageResourceId) {
        this.mDefaultTranslation = defaultTransaltion;
        this.mMiwokTranslation = MiwokTranslation;
        this.mImageResourceId = ImageResourceId;
    }

    //Get the default translation of the word
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    //Get Miwok Translation of the word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourceId() { return mImageResourceId; }
}
