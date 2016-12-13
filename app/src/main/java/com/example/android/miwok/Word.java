package com.example.android.miwok;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;

    public Word(String defaultTransaltion, String MiwokTranslation) {
        this.mDefaultTranslation = defaultTransaltion;
        this.mMiwokTranslation = MiwokTranslation;
    }

    //Get the default translation of the word
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    //Get Miwok Translation of the word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }
}
