package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class disease_searchActivity extends AppCompatActivity {

    private ImageButton search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_search);

        search_button = (ImageButton) findViewById(R.id.search_button);

        search_button.setOnClickListener(new View.OnClickListener(){
            //검색 이미지버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                if (v == search_button) {
                    Intent intent = new Intent(getApplicationContext(), desease_detail.class);
                    startActivity(intent);
                }
            }
        });


    }
}
