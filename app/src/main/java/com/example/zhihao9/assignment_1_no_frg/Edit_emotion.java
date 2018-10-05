package com.example.zhihao9.assignment_1_no_frg;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Edit_emotion extends AppCompatActivity {
//    initialzied all the variable
    TimePickerDialog timepickerdialog;
    DatePickerDialog datepickerdialog;
    public Calendar cal = Calendar.getInstance();
    int current_hour;
    int current_minute;
    int year;
    int month;
    int day;


    private static final String FILENAME = "file.sav";
    private ArrayList<emotion_detail> emotions_details = new ArrayList<emotion_detail>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        loadFromFile();
        setContentView(R.layout.activity_edit_emotion);
        TextView emotion_title = (TextView) findViewById(R.id.edit_emotion_title);
//        get the info from the preview internt
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        Log.d("title",title);
        emotion_title.setText(title);

    }

    @Override
    protected void onStart() {
        //        The datepicker and timepicker tutorial can be found following url
//        https://abhiandroid.com/ui/datepicker
//        http://www.geekcodehub.com/2018/05/23/custome-time-picker-in-android/
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        String timestamp = bundle.getString("timestamp");
        String description = bundle.getString("description");
        final int item_id = bundle.getInt("position");
//          debugging
//        Log.d("timestamo",timestamp);
//        Log.d("description",description);
        EditText datepicker = (EditText) findViewById(R.id.edit_Date_picker);
        EditText timepicker = (EditText) findViewById(R.id.edit_Time_picker);
        EditText his_description = (EditText) findViewById(R.id.edit_emotion_description);
        String[] timeparts = timestamp.split("T");

        datepicker.setText(timeparts[0]);
        final String[] dateparts = timeparts[0].split("-");
        timepicker.setText(timeparts[1]);
        final String[] hour_mins = timeparts[1].split(":");

        his_description.setText(description);

//create a timepicker for picking a time
        final EditText edit_timepicker = findViewById(R.id.edit_Time_picker);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                current_hour = Integer.parseInt(hour_mins[0]);
                current_minute = Integer.parseInt(hour_mins[1]);

                timepickerdialog = new TimePickerDialog(Edit_emotion.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        edit_timepicker.setText(i +":" + i1);
                    }
                }, current_hour,current_minute,true);
                timepickerdialog.show();
            }
        });

        final EditText edit_datepicker  = findViewById(R.id.edit_Date_picker);
//create a datepicker for picking a date
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                year = Integer.parseInt(dateparts[0]);
                month = Integer.parseInt(dateparts[1]);
                day = Integer.parseInt(dateparts[2]);

                datepickerdialog = new DatePickerDialog(Edit_emotion.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                Date date = new Date(i, i1, i2-1);
                        i1 = i1+1;
                        edit_datepicker.setText(i +"-" + i1 +"-"+ i2);
                    }
                },year,month-1,day);
                datepickerdialog.show();

            }
        });

//        impeletment the save and delete button
        final AppCompatButton save_btn = (AppCompatButton) findViewById(R.id.edit_save_btn);
        AppCompatButton del_btn = (AppCompatButton) findViewById(R.id.edit_del_btn);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText new_description = (EditText) findViewById(R.id.edit_emotion_description);
                EditText new_date = (EditText)findViewById(R.id.edit_Date_picker);
                EditText new_time = (EditText)findViewById(R.id.edit_Time_picker);
                String new_date_string = new_date.getText().toString();
                String new_time_stirng = new_time.getText().toString();
                String[] dateparts = new_date_string.split("-");
                String[] timeparts = new_time_stirng.split(":");
                String new_descritipn_string = new_description.getText().toString();
                String edit_item_title = emotions_details.get(item_id).getEmotion_title();
                emotion_detail selected_item = emotions_details.get(item_id);
                selected_item.setEmotion_description(new_descritipn_string);
                Date new_timestampe = new Date(Integer.parseInt(dateparts[0])-1900,Integer.parseInt(dateparts[1])-1,Integer.parseInt(dateparts[2]),Integer.parseInt(timeparts[0]),Integer.parseInt(timeparts[1]));
                selected_item.setTimestamp(new_timestampe);
                saveInFile(emotions_details);
                Toast.makeText(Edit_emotion.this,edit_item_title+"edit successfully",Toast.LENGTH_SHORT);
                Intent backtohome = new Intent(Edit_emotion.this,MainActivity.class);
                startActivity(backtohome);
            }
        });


        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delete_item_title = emotions_details.get(item_id).getEmotion_title();
                emotions_details.remove(item_id);
                Log.d("edit_emotion" , "delete"+ item_id);
//                Log.d("edit_emotion" , "delete"+ emotions_details.get(0).getEmotion_title());
//                Log.d("edit_emotion" , "delete"+ emotions_details.get(1).getEmotion_title());
                Toast.makeText(getApplicationContext(), delete_item_title+"remove successfully",
                        Toast.LENGTH_SHORT).show();
                saveInFile(emotions_details);
                Intent backtohome = new Intent(Edit_emotion.this,add_emotion_list.class);
                startActivity(backtohome);
            }
        });


    }

//    save the array list in the internal memory by json format

    private void saveInFile(ArrayList<emotion_detail> emotion_details) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            gson.toJson(emotion_details,writer);
            writer.flush();
            fos.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
            Log.d("banana",emotions_details.get(0).getEmotion_title());

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
