package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class disease_listActivity extends AppCompatActivity {

    public ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_list);

        ArrayList<String> getserver = getIntent().getExtras().getStringArrayList("selects");

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listview = (ListView) findViewById(R.id.diseaseView);
        listview.setAdapter(adapter);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        for(final String str : getserver){
            for(int i=0; i<40; i++){
                final String num = Integer.toString(i);
                final DatabaseReference mDatabase = database.child("diseases").child(num);
                mDatabase.child("animal").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String animal_type = dataSnapshot.getValue().toString();
                            if(animal_type.contains(str)){
                                mDatabase.child("condition").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists()){
                                            String disease_condition = dataSnapshot.getValue().toString();
                                            if (disease_condition.contains(str)) {
                                                mDatabase.child("diease_name").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        String name = dataSnapshot.getValue().toString();
                                                        adapter.add(name);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        Intent no = new Intent(getApplicationContext(), NoDiseaseActivity.class);
                                                        startActivity(no);
                                                    }
                                                });
                                            }else{
                                                Intent no = new Intent(getApplicationContext(), NoDiseaseActivity.class);
                                                startActivity(no);
                                            }

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Intent no = new Intent(getApplicationContext(), NoDiseaseActivity.class);
                                        startActivity(no);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview의 item클릭시 detail로 넘어가기
                Intent intent = new Intent(getApplicationContext(), disease_detail.class);

                //intent객체에 데이터를 실어서 보내기
                String disease_name = ((TextView)view).getText().toString();
                intent.putExtra("name", disease_name);
                startActivity(intent);
            }
        });
    }

}
