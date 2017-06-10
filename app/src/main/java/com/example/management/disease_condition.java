package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class disease_condition extends AppCompatActivity {

    private Button condition;
    private Button disease_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_condition);

        condition = (Button) findViewById(R.id.condition);
        disease_name = (Button) findViewById(R.id.disease_name);


        disease_name.setOnClickListener(new View.OnClickListener(){
            //질병명 검색 눌렀을 때
            @Override
            public void onClick(View v) {
                if (v == disease_name) {
                    Intent intent = new Intent(getApplicationContext(), disease_searchActivity.class);
                    startActivity(intent);
                }
            }
        });
        condition.setOnClickListener(new View.OnClickListener(){
            //증상으로 검색 눌렀을 때
            @Override
            public void onClick(View v) {
                if (v == condition) {
                    Intent intent = new Intent(getApplicationContext(), condition_searchActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.homeButton:
                //homeButton이 눌렸을 경우 이벤트 발생
                startActivity(new Intent(this, MainActivity.class));

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}