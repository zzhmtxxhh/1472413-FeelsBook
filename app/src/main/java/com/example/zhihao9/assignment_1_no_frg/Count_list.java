package com.example.zhihao9.assignment_1_no_frg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Count_list extends AppCompatActivity {
//    initialize all the variable
    private static final String FILENAME = "file.sav";
    private ArrayList<emotion_detail> emotions_details = new ArrayList<emotion_detail>();
    int joy_count = 0;
    int love_count = 0;
    int superise_count = 0;
    int anger_count = 0 ;
    int sadness_count = 0;
    int fear_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_list);
    //initalize all the view by id
        TextView joy_c = (TextView) findViewById(R.id.joy_count);
        TextView love_c = (TextView) findViewById(R.id.love_count);
        TextView superise_c = (TextView) findViewById(R.id.surprise_count);
        TextView anger_c = (TextView) findViewById(R.id.anger_count);
        TextView sadness_c = (TextView) findViewById(R.id.sadness_count);
        TextView fear_c = (TextView) findViewById(R.id.fear_count);

        loadFromFile();
//        reset all the counting object
        joy_count = 0;
        love_count = 0;
        superise_count = 0;
        anger_count = 0 ;
        sadness_count = 0;
        fear_count = 0;
//        a for and if statement to count the number of the emotion
        for (int counter = 0; counter < emotions_details.size(); counter++) {
            Log.d("counter", emotions_details.get(counter).getEmotion_title());
            if (emotions_details.get(counter).getEmotion_title().equals("joy")) {
                joy_count++;
            }
            else if (emotions_details.get(counter).getEmotion_title().equals("love")) {
                love_count++;
            }
            else if (emotions_details.get(counter).getEmotion_title().equals("surperise")) {
                superise_count++;
            }
            else if (emotions_details.get(counter).getEmotion_title().equals("anger")) {
                anger_count++;
            }

            else if (emotions_details.get(counter).getEmotion_title().equals("sadness")) {
                sadness_count++;
            }
            else if (emotions_details.get(counter).getEmotion_title().equals("fear")) {
                fear_count++;
            }

        }

//
//         debugging
//        Log.d("count","joy :   "+joy_count);
//        Log.d("count","love :   "+love_count);
//        Log.d("count","suprise  :   "+superise_count);
//        Log.d("count","anger  :   "+anger_count);
//        Log.d("count","fear :   "+fear_count);
//        Log.d("count","sadness :   "+sadness_count);

//        set all the value to the textview

        joy_c.setText(""+joy_count);
        love_c.setText(""+love_count);
        superise_c.setText(""+superise_count);
        anger_c.setText(""+anger_count);
        sadness_c.setText(""+sadness_count);
        fear_c.setText(""+fear_count);

    }

    @Override
    protected void onStart() {
        super.onStart();



    }

//    Pirvate method loadfile() laod the json file form the internal memeory

    private void loadFromFile() {
        try {
            Log.d("banana",FILENAME);
            FileInputStream fis = openFileInput(FILENAME);
            Log.d("banana","2");
            InputStreamReader isr = new InputStreamReader(fis);
            Log.d("banana","3");
            BufferedReader reader  = new BufferedReader(isr);
            Log.d("banana","4");
            Gson gson = new Gson();
            Log.d("banana","5");
            Type listTweetType = new TypeToken<ArrayList<emotion_detail>>(){}.getType();
            Log.d("banana","6");
            emotions_details = gson.fromJson(reader,listTweetType);
            Log.d("banana","7");

//            error handling

        } catch (FileNotFoundException e) {


            emotions_details = new ArrayList<emotion_detail>();
            e.printStackTrace();
            Log.d("banana","8");
        } catch (IOException e) {

            e.printStackTrace();
            Log.d("banana","9");
        }
    }
}
