package com.example.management;

/**
 * Created by Chaewon on 2017-06-08.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VetlistAdapter extends BaseAdapter {

    //declare variables
    Context mcontext;
    LayoutInflater inflater;
    private List<VetlistItem> items= null;
    private ArrayList<VetlistItem> arrayList;

    public VetlistAdapter(Context context, List<VetlistItem> item){
        mcontext= context;
        this.items= item;
        inflater= LayoutInflater.from(mcontext);
        this.arrayList= new ArrayList<>();
        this.arrayList.addAll(item);
    }

    public class ViewHolder{
        TextView vetName;
        TextView vetAddr;
        TextView vetTel;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public VetlistItem getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent){
        final ViewHolder holder;
        if (view==null){
            holder= new ViewHolder();
            view= inflater.inflate(R.layout.vetlist_item, null);
            //locate textviews into vetlist_itemxml
            holder.vetName= (TextView) view.findViewById(R.id.vetName);
            holder.vetAddr= (TextView) view.findViewById(R.id.vetAddr);
            holder.vetTel= (TextView) view.findViewById(R.id.vetTel);
            view.setTag(holder);
        }
        else{
            holder= (ViewHolder) view.getTag();
        }
        //set the results into textviews
        holder.vetName.setText(items.get(position).getName());
        holder.vetAddr.setText(items.get(position).getAddr());
        holder.vetTel.setText(items.get(position).getTel());

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mcontext, VetInfoActivity.class);
                intent.putExtra("vetName", (items.get(position).getName()));
                intent.putExtra("vetAddress", (items.get(position).getAddr()));
                intent.putExtra("vetTel", (items.get(position).getTel()));
                mcontext.startActivity(intent);
            }
        });
        return view;
    }

    //filter class

    public void filter(String charText){
        charText= charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length()==0){
            items.addAll(arrayList);
        }
        else{
            for (VetlistItem item: arrayList){
                if (item.getAddr().toLowerCase(Locale.getDefault()).contains(charText)){
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
