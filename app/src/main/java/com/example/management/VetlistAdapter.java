package com.example.management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Chaewon on 2017-05-18.
 */

public class VetlistAdapter extends BaseAdapter {
    private LayoutInflater listInflater;
    private ArrayList<VetlistItem> data;
    private int layout;

    public VetlistAdapter(Context context, int layout, ArrayList<VetlistItem> data){
        this.listInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data= data;
        this.layout= layout;
    } //constructer of this class

    @Override
    public int getCount(){
        return data.size();
    } //return the number of data used in Adapter

    @Override
    public String getItem(int position){
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = listInflater.inflate(layout, parent, false);
        } //return the view usd for print out data in the exact position

        VetlistItem listviewitem = data.get(position);

        TextView name = (TextView) convertView.findViewById(R.id.VetName);
        name.setText(listviewitem.getName());

        return convertView;
    }
    public void addItem(String name, String addr, String tel){
        VetlistItem item= new VetlistItem();

        item.setName(name);
        item.setAddr(addr);
        item.setTel(tel);

        data.add(item);
    }

}