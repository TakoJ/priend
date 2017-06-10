package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class disease_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        TextView name = (TextView) findViewById(R.id.disease_name);
        final TextView define = (TextView) findViewById(R.id.define);
        final TextView condition = (TextView) findViewById(R.id.condition);
        final TextView cause = (TextView) findViewById(R.id.cause);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));

        final String names = name.getText().toString();


        for (int i = 1; i < 40; i++) {
            final String num = Integer.toString(i);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference().child("diseases").child(num);

            myRef.child("diease_name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String disease_name = dataSnapshot.getValue().toString();
                        if(disease_name.equals(names)){
                            myRef.child("define").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String disease_define = dataSnapshot.getValue().toString();
                                    define.setText(disease_define);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            myRef.child("condition").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String disease_condition1 = dataSnapshot.getValue().toString();
                                    String disease_condition2 = disease_condition1.replace("[","");
                                    String disease_condition3 = disease_condition2.replace("],","\n");
                                    String disease_condition = disease_condition3.replace("]]","");
                                    condition.setText(disease_condition);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            myRef.child("cause").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String disease_cause = dataSnapshot.getValue().toString();
                                    cause.setText(disease_cause);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            return;
                        };
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
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
