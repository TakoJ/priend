package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Chaewon on 2017-05-22.
 */

public class VetlistActivity extends VetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetlist);

        String[] vets = {"A동물병원", "B동물병원", "C동물병원", "D동물병원", "E동물병원", "F동물병원", "G동물병원", "I동물병원"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, vets);

        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(i));

                    }
                }
        );
        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(getApplicationContext(), mapActivity.class);
                startActivity(intent);
            }
        });
    }
    ///////////////리스트 item 누르면 상세 페이지로 넘어가게///////////////
    //////////////오류 발생: fragment 사용하지 말것. no view found for fragment vetDetailActivity///////////////////////
}