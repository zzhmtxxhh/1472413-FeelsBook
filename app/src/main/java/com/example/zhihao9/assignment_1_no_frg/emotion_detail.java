package com.example.zhihao9.assignment_1_no_frg;
import android.support.annotation.NonNull;

import java.util.Date;


//The emotion_detail class more description can read the UML class explanation.
//The emotion_detail class is extented by emotion class
public class emotion_detail extends emotion implements Comparable<emotion_detail>{

    private String emotion_description;


    public emotion_detail(String emotion_title,String emotion_description) {
        super(emotion_title);
        this.emotion_description = emotion_description;
    }

    public emotion_detail(String emotion_title, Date timestamp , String emotion_description) {
        super(emotion_title, timestamp);
        this.emotion_description = emotion_description;
    }

    public String getEmotion_description() {
        return emotion_description;
    }

    public void setEmotion_description(String emotion_description) {
        this.emotion_description = emotion_description;
    }

    @Override
    public int compareTo(@NonNull emotion_detail emotion_detail) {
        return getTimestamp().compareTo(emotion_detail.getTimestamp());
    }
}