package com.example.zhihao9.assignment_1_no_frg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class add_emotion_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emotion_list);


//        Assign the view by id
        ListView listView = (ListView) findViewById(R.id.emotion_add_list);
        emotion_list_adapter adapter = new emotion_list_adapter (add_emotion_list.this,R.layout.sample_list_add,get_Emontion_list());
        listView.setAdapter(adapter);
//        create a listitemonclicklisterner
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = ((TextView) view.findViewById(R.id.emotion_title_add)).getText().toString();
                Intent Emotion_add = new Intent(add_emotion_list.this,Add_emotion.class);
                Toast.makeText(add_emotion_list.this,selected,Toast.LENGTH_LONG).show();
//                tranfer infomation to other intent
                Emotion_add.putExtra("message",selected);
                startActivity(Emotion_add);

            }
        });

//initialize button
        AppCompatButton gohistory = (AppCompatButton) findViewById(R.id.History_btn);
        AppCompatButton gocount = (AppCompatButton) findViewById(R.id.count_btn);
//set on click listenr
        gohistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gohis = new Intent(add_emotion_list.this , MainActivity.class);
                startActivity(gohis);

            }
        });

        gocount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gocount = new Intent(add_emotion_list.this , Count_list.class);
                startActivity(gocount);

            }
        });
    }


//    The array list of all emotions

    public ArrayList<emotion> get_Emontion_list(){
        ArrayList<emotion> emotion_list = new ArrayList<emotion>();
        emotion joy = new emotion("joy");
        emotion_list.add(joy);
        emotion love = new emotion("love");
        emotion_list.add(love);
        emotion superise = new emotion("surperise");
        emotion_list.add(superise);
        emotion anger = new emotion("anger");
        emotion_list.add(anger);
        emotion sadness = new emotion("sadness");
        emotion_list.add(sadness);
        emotion fear = new emotion("fear");
        emotion_list.add(fear);
        return  emotion_list;
    }


}
