package com.example.management;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Chaewon on 2017-05-18.
 */

public class listActivity extends ArrayAdapter<String> {
        listActivity(Context context, String[]items){
            super(context, R.layout.activity_list_layout, items);
        }

///////////////////////vetActivity로 이동///////////////////////
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater listInflater = LayoutInflater.from(getContext());
        View view = listInflater.inflate(R.layout.activity_list_layout, parent, false);
        String item = getItem(position);
        TextView textview = (TextView) view.findViewById(R.id.VetName);
        TextView text_view = (TextView) view.findViewById(R.id.VetAddr);
        textview.setText(item);
        text_view.setText(item);
        return view;
    }
}