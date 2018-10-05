package com.example.zhihao9.assignment_1_no_frg;

import java.text.SimpleDateFormat;
import java.util.Date;

//The emotion class more description can read the UML class explanation
public class emotion{



    private Date timestamp;
    private String emotion_title;


    public emotion(String emotion_title){
        this.emotion_title = emotion_title;
        this.timestamp = new Date();
    }

    public emotion(String emotion_title,Date timestamp){
        this.emotion_title = emotion_title;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getEmotion_title() {
        return emotion_title;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setEmotion_title(String emotion_title) {
        this.emotion_title = emotion_title;
    }

    public String getTimestampString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String time = format.format(this.timestamp.getTime());
        return time;
    }

}
