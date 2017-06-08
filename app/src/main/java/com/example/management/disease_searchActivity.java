package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class disease_searchActivity extends AppCompatActivity {

    private EditText search;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_search);

        search = (EditText) findViewById(R.id.search);

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        for (int i = 1; i < 40; i++) {
            //일단 질병 40개만 받음.
            final String num = Integer.toString(i);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference().child("diseases").child(num);

            myRef.child("diease_name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.getValue().toString();
                        adapter.add(name);
                    } else {
                        System.out.println("없음");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                //text가 바뀔때 수행되는 함수
                String filterText = s.toString();
                if(filterText.length()>0){
                    //글자 입력시
                    listview.setFilterText(filterText);
                }else{
                    listview.clearTextFilter();
                }
            }
        });

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
