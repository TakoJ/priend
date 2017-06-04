package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class condition_searchActivity extends AppCompatActivity {

    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_search);

        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener(){
            //검색버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                if (v == search) {
                    Intent intent = new Intent(getApplicationContext(), disease_listActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
