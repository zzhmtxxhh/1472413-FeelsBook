package com.example.zhihao9.assignment_1_no_frg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<emotion_detail> emotions_details = new ArrayList<emotion_detail>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        Collections.sort(emotions_details);
//        intialize the view by its id

        AppCompatButton add_emotion_btn = (AppCompatButton) findViewById(R.id.add_emotion_btn);
        add_emotion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_add_emotion_list = new Intent(MainActivity.this, add_emotion_list.class);
                startActivity(go_add_emotion_list);
            }
        });


        ListView listView = (ListView) findViewById(R.id.emotion_his_list);
        emotion_list_detail_adapter adapter = new emotion_list_detail_adapter (MainActivity.this,R.layout.sample_list_his,emotions_details);
        listView.setAdapter(adapter);

// create a listitemlistener to access the emotion list.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_his_title = ((TextView) view.findViewById(R.id.his_emotion_title)).getText().toString();
                String selected_his_time = ((TextView) view.findViewById(R.id.his_emotion_date)).getText().toString();
                String selected_his_decripton = ((TextView) view.findViewById(R.id.his_emotion_description)).getText().toString();
                Toast.makeText(MainActivity.this,selected_his_decripton,Toast.LENGTH_LONG).show();
                Intent edit_emotion = new Intent(MainActivity.this, Edit_emotion.class);
                edit_emotion.putExtra("title",selected_his_title);
                edit_emotion.putExtra("timestamp",selected_his_time);
                edit_emotion.putExtra("description",selected_his_decripton);
                edit_emotion.putExtra("position",i);
                startActivity(edit_emotion);
            }
        });

        AppCompatButton count_his = (AppCompatButton) findViewById(R.id.Count_list_btn);

        count_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent count_his = new Intent(MainActivity.this,Count_list.class);
                startActivity(count_his);
            }
        });




    }

    //    load the data from internal memeory by json format
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
