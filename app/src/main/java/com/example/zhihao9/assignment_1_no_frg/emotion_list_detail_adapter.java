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

//a controller for view and model(emotion_detail)'s communication
//totorial can found in here
//https://www.sitepoint.com/custom-data-layouts-with-your-own-android-arrayadapter/
public class emotion_list_detail_adapter extends ArrayAdapter<emotion_detail> {

    private int resourceId;

    public emotion_list_detail_adapter(@NonNull Context context, int resource, @NonNull ArrayList<emotion_detail> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        emotion_detail emotion_detail = getItem(position);
        View view ;
        viewholder viewHolder;

        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);

            viewHolder = new viewholder();

            viewHolder.emotion_title = (TextView) view.findViewById(R.id.his_emotion_title);
            viewHolder.emotion_description = (TextView) view.findViewById(R.id.his_emotion_description);
            viewHolder.timestamp= (TextView) view.findViewById(R.id.his_emotion_date);

            view.setTag(viewHolder);


        }else{
            view = convertView;
            viewHolder = (viewholder) view.getTag();
        }

        viewHolder.emotion_title.setText(emotion_detail.getEmotion_title());
        viewHolder.emotion_description.setText(emotion_detail.getEmotion_description());
        viewHolder.timestamp.setText((emotion_detail.getTimestampString()));

        return view ;




    }

    class viewholder{
        TextView emotion_title;
        TextView emotion_description;
        TextView timestamp;
    }
}
