package com.example.management;

/*
17.06.02
수정할 내용
list item을 DB에 연동
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Chaewon on 2017-05-22.
 */

public class VetlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetlist);

        ListView listview = (ListView) findViewById(R.id.listView);
        ArrayList<VetlistItem> data = new ArrayList<>();
        VetlistAdapter adapter = new VetlistAdapter(this, R.layout.activity_list_layout, data);
        listview.setAdapter(adapter);

        adapter.addItem("vet1", "서울시 성동구", "02-2220-2220");
        adapter.addItem("vet2", "서울시 종로구", "02-1110-1110");
        adapter.addItem("vet3", "서울시 강남구", "02-3330-3330");

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parentView, View view, int i, long id) {
                        /*i: position of the list
                        id: in the case of listview, position of the list.
                         */
                        VetlistItem item = (VetlistItem) parentView.getItemAtPosition(i);
                        Bundle extras = new Bundle();
                        //bundle data that will be transferred to the next activity
                        extras.putString("VetName", item.getName());
                        extras.putString("VetAddress", item.getAddr());
                        extras.putString("VetTel", item.getTel());

                        Intent intent = new Intent(getApplicationContext(), vetDetailActivity.class);
                        intent.putExtras(extras); //put the bundle data into the intent
                        startActivity(intent);
                    }
                }
        );
    }
}