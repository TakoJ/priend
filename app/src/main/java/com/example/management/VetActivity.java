package com.example.management;

/**
 * Created by Chaewon on 2017-05-18.
 */

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Button;

public class VetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.vet_list).setOnClickListener(mClickListener);

        String[] vets= {"A동물병원", "B동물병원", "C동물병원", "D동물병원", "E동물병원", "F동물병원", "G동물병원", "I동물병원"};
        ListAdapter adapter= new ListAdapter(this, vets);
        ListView listview= (ListView) findViewById(R.id.vet_list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long id){
                        String item= String.valueOf(parent.getItemAtPosition(i));
                        FragmentManager manager= getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.vet_detail, new vetDetailActivity()).commit();
                    }
                }
        );
    }

    Button.OnClickListener mClickListener= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Intent intent= new Intent(
                    getApplicationContext(),
                    listAdapter.class);
            startActivity(intent);
        }
    };
}
