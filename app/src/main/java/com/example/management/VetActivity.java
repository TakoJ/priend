package com.example.management;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class VetActivity extends AppCompatActivity {
    ImageButton searchVetButton;
    ImageButton mapButton;
    private final Context context= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchVetButton= (ImageButton) findViewById(R.id.SearchVetButton);
        mapButton= (ImageButton) findViewById(R.id.mapButton);

        searchVetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, VetlistActivity.class);
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(context, mapActivity.class);
                startActivity(intent);
            }
        });
    }
///////////////////////////////////메인에는 검색버튼 눌렀을 때 이벤트///////////////////////////////////////////////////////
    //////////////////////자동완성 정규식 --> 일단 이후에 생각하는 걸로/////////////////////////////

    /////////////////맵 버튼도 구현/////////////////////
}