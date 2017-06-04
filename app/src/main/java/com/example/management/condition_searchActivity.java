package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class condition_searchActivity extends AppCompatActivity implements View.OnClickListener{

    private Button vomit, torpor, hairloss, stomachache,nausea, polypnea, constipation, inappetence;
    private Button lump, cough, fever, nosebleed, diarrhea, redpee, death, dyspnea, shiver, infection, swelling, pain;
    private Button search;

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

        vomit.setOnClickListener(this); torpor.setOnClickListener(this); hairloss.setOnClickListener(this); stomachache.setOnClickListener(this);
        nausea.setOnClickListener(this); polypnea.setOnClickListener(this); constipation.setOnClickListener(this); inappetence.setOnClickListener(this);
        lump.setOnClickListener(this); cough.setOnClickListener(this); fever.setOnClickListener(this); nosebleed.setOnClickListener(this);
        diarrhea.setOnClickListener(this); redpee.setOnClickListener(this); death.setOnClickListener(this); dyspnea.setOnClickListener(this);
        shiver.setOnClickListener(this); infection.setOnClickListener(this); swelling.setOnClickListener(this); pain.setOnClickListener(this);

        search.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == search) {
            //검색버튼 눌렀을 때
            Intent intent = new Intent(getApplicationContext(), disease_listActivity.class);
            startActivity(intent);
        }else{
            if(v.isSelected() == false){
                v.setSelected(true);
            }else{
                v.setSelected(false);
            }
        }

        }
    }
