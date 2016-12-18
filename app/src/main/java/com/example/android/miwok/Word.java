package com.example.android.miwok;

public class Word {

    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageResourceId = No_Image_Provided; // always start with false

    private static final int No_Image_Provided = -1;

    private int mAudioResourceId;

    public Word(String defaultTransaltion, String MiwokTranslation, int AudioResourceId ) {
        this.mDefaultTranslation = defaultTransaltion;
        this.mMiwokTranslation = MiwokTranslation;
        this.mAudioResourceId = AudioResourceId;
    }

    public Word(String defaultTransaltion, String MiwokTranslation, int ImageResourceId, int AudioResourceId) {
        this.mDefaultTranslation = defaultTransaltion;
        this.mMiwokTranslation = MiwokTranslation;
        this.mImageResourceId = ImageResourceId;
        this.mAudioResourceId = AudioResourceId;
    }

    //Get the default translation of the word
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    //Get Miwok Translation of the word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public Integer getImageResourceId() { return mImageResourceId; }

    public boolean hasImage() {
        // mImage ResourceId != -1 (false), then it returns true
        return mImageResourceId != No_Image_Provided;
    }

    // return the audio resource id of the word
    public int getAudioResourceId() {return mAudioResourceId; }
}
