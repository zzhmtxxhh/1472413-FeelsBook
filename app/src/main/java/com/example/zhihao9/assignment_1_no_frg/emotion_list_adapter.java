package com.example.zhihao9.assignment_1_no_frg;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//a controller for view and model(emotion)'s communication
//tutorial can found in here
//https://www.sitepoint.com/custom-data-layouts-with-your-own-android-arrayadapter/
public class emotion_list_adapter extends ArrayAdapter<emotion> {

    private int resourceId;

    public emotion_list_adapter(Context context ,int textViewResourceId ,ArrayList<emotion> objects){
        super(context, textViewResourceId,objects);
        resourceId = textViewResourceId;

    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        emotion emotion = getItem( position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);

            viewHolder = new ViewHolder();

            viewHolder.emotion_title = (TextView) view.findViewById(R.id.emotion_title_add);

            view.setTag(viewHolder);


        }else{
            view = convertView;

            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.emotion_title.setText(emotion.getEmotion_title());

        return view;
    }


    class ViewHolder{
        TextView emotion_title;
    }
}
