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

public class Add_emotion extends AppCompatActivity {

    TimePickerDialog timepickerdialog;
    DatePickerDialog datepickerdialog;
    ArrayList<emotion> emotions_list = new ArrayList<emotion>();
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
        setContentView(R.layout.activity_add_emotion);
        loadFromFile();
        Bundle bundle = getIntent().getExtras();
        TextView emotion_title = (TextView) findViewById(R.id.edit_emotion_title);
        String title = bundle.getString("message");
        emotion_title.setText(title);
    }

    @Override
    protected void onStart() {
//        The datepicker and timepicker tutorial can be found following url
//        https://abhiandroid.com/ui/datepicker
//        http://www.geekcodehub.com/2018/05/23/custome-time-picker-in-android/
        super.onStart();

        final EditText timepicker = findViewById(R.id.Time_picker);

        current_hour = cal.get(Calendar.HOUR_OF_DAY);
        current_minute = cal.get(Calendar.MINUTE);

        timepicker.setText(current_hour+":"+current_minute+":"+"00");

        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                timepickerdialog = new TimePickerDialog(Add_emotion.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        timepicker.setText(i +":" + i1);
                    }
                }, current_hour,current_minute,true);
                timepickerdialog.show();
            }
        });

        final EditText datepicker  = findViewById(R.id.Date_picker);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        int month_modify = month+1;
        datepicker.setText(year+"-"+month_modify+"-"+day);

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                        datepickerdialog = new DatePickerDialog(Add_emotion.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                i1 = i1;
//                                Date date = new Date(i, i1, i2-1);
                                datepicker.setText(i +"-" + i1 +"-"+ i2);
                            }
                        },year,month,day);
                        datepickerdialog.show();

            }
        });


        AppCompatButton add_button = (AppCompatButton) findViewById(R.id.emotion_save);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emotion_description  = (EditText) findViewById(R.id.edit_text);
                String description = emotion_description.getText().toString();
                if (description == null){ description = ""; }
                Bundle bundle = getIntent().getExtras();
                TextView emotion_title = (TextView) findViewById(R.id.edit_emotion_title);
                String title = bundle.getString("message");
                String time = timepicker.getText().toString();
                String date = datepicker.getText().toString();

                String[] timeparts = time.split(":");
                String[] dateparts = date.split("-");

                Date timestampe = new Date(Integer.parseInt(dateparts[0])-1900,Integer.parseInt(dateparts[1])-1,Integer.parseInt(dateparts[2]),Integer.parseInt(timeparts[0]),Integer.parseInt(timeparts[1]));
                emotion_detail emotion_detail = new emotion_detail(title,timestampe,description );
                Log.d("timestampe" , emotion_detail.getTimestampString());
                emotions_details.add(emotion_detail);
                Toast.makeText(getApplicationContext(), emotion_detail.getEmotion_title()+"add successfully",
                        Toast.LENGTH_SHORT).show();
                saveInFile(emotions_details);
                Intent backtohome = new Intent(Add_emotion.this,add_emotion_list.class);
                startActivity(backtohome);
            }
        });
    }

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
//            Log.d("banana",emotions_details.get(0).getEmotion_title());

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
