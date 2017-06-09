package com.example.management;

/**
 * Created by Chaewon on 2017-06-08.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VetInfoActivity extends Activity{
    //declare variables
    TextView vetName;
    TextView vetAddr;
    TextView vetTel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetdetail);
        //retrieve data from mainactivity on item click event

        // locate the textviews in vetdetail.xml
        vetName= (TextView) findViewById(R.id.vetname);
        vetAddr= (TextView) findViewById(R.id.vetaddr);
        vetTel= (TextView) findViewById(R.id.vettel);

        Intent intent= getIntent();
        //get result of the vetName
        vetName.setText(intent.getStringExtra("vetName"));
        vetAddr.setText(intent.getStringExtra("vetAddress"));
        vetTel.setText(intent.getStringExtra("vetTel"));

        Button mapButton= (Button) findViewById(R.id.button);
        mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(getApplicationContext(), mapActivity.class);
                intent.putExtra("var", 2);
                startActivity(intent);
            }
        });
    }
}
