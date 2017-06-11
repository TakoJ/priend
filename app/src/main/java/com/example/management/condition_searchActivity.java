package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class condition_searchActivity extends AppCompatActivity implements View.OnClickListener{

//    private RadioButton dog;
//    private RadioButton cat;
    private Button vomit, torpor, hairloss, stomachache,nausea, polypnea, constipation, inappetence;
    private Button lump, cough, fever, nosebleed, diarrhea, redpee, death, dyspnea, shiver, infection, swelling, pain;
    private Button search;

    public ArrayList<String> toserver = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_search);

        vomit = (Button) findViewById(R.id.vomit);
        torpor = (Button) findViewById(R.id.torpor);
        hairloss = (Button) findViewById(R.id.hairloss);
        stomachache = (Button) findViewById(R.id.stomachache);
        nausea = (Button) findViewById(R.id.nausea);
        polypnea = (Button) findViewById(R.id.polypnea);
        constipation = (Button) findViewById(R.id.constipation);
        inappetence = (Button) findViewById(R.id.inappetence);
        lump = (Button) findViewById(R.id.lump);
        cough = (Button) findViewById(R.id.cough);
        fever = (Button) findViewById(R.id.fever);
        nosebleed = (Button) findViewById(R.id.nosebleed);
        diarrhea = (Button) findViewById(R.id.diarrhea);
        redpee = (Button) findViewById(R.id.redpee);
        death = (Button) findViewById(R.id.death);
        dyspnea = (Button) findViewById(R.id.dyspnea);
        shiver = (Button) findViewById(R.id.shiver);
        infection = (Button) findViewById(R.id.infection);
        swelling = (Button) findViewById(R.id.swelling);
        pain = (Button) findViewById(R.id.pain);
        search = (Button) findViewById(R.id.search);

        vomit.setOnClickListener(MyListener); torpor.setOnClickListener(MyListener); hairloss.setOnClickListener(MyListener); stomachache.setOnClickListener(MyListener);
        nausea.setOnClickListener(MyListener); polypnea.setOnClickListener(MyListener); constipation.setOnClickListener(MyListener); inappetence.setOnClickListener(MyListener);
        lump.setOnClickListener(MyListener); cough.setOnClickListener(MyListener); fever.setOnClickListener(MyListener); nosebleed.setOnClickListener(MyListener);
        diarrhea.setOnClickListener(MyListener); redpee.setOnClickListener(MyListener); death.setOnClickListener(MyListener); dyspnea.setOnClickListener(MyListener);
        shiver.setOnClickListener(MyListener); infection.setOnClickListener(MyListener); swelling.setOnClickListener(MyListener); pain.setOnClickListener(MyListener);

        search.setOnClickListener(this);
//        dog.setOnClickListener(optionListener);
//        cat.setOnClickListener(optionListener);
    }

//    View.OnClickListener optionListener = new View.OnClickListener(){
//        public void onClick(View v){
//            RadioButton radio = (RadioButton)v;
//            if(dog.isChecked()){
//                String dog = radio.getText().toString();
//                toserver.add(dog);
//            }else if(cat.isChecked()){
//                String cat = radio.getText().toString();
//                toserver.add(cat);
//            }
//        }
//    };

    View.OnClickListener MyListener = new View.OnClickListener(){
        public void onClick(View v) {
            int pos;
            Button btn = (Button)v;
            if (!v.isSelected()) {
                v.setSelected(true);
                String selected = btn.getText().toString();
                toserver.add(selected);
            } else {
                v.setSelected(false);
                String unselected = btn.getText().toString();
                pos = toserver.indexOf(unselected);
                toserver.remove(pos);
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v == search){
        //검색버튼 눌렀을 때
        Bundle list_bundle = new Bundle();
        Intent intent=new Intent(getApplicationContext(),disease_listActivity.class);
        intent.putStringArrayListExtra("selects", toserver);
        this.startActivity(intent);
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
