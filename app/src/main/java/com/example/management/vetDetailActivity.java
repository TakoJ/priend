package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by Chaewon on 2017-05-18.
 */

public class vetDetailActivity extends Fragment { //fragment 써야하는 이유?
    View v;
    ImageButton VetAddrMap;
    LinearLayout resultView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_vet_detail, container, false);

        //inflater 공부

        VetAddrMap= (ImageButton) v.findViewById(R.id.VetAddrMap);
        resultView= (LinearLayout) v.findViewById(R.id.vet_detail);


        VetAddrMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(getActivity(),mapActivity.class);
                startActivity(intent);
            }
            //fragment 내에서는 this가 사용이 불가능하다, fragment와 activity가 겹쳐서 올라가있는 상태인데, this라고 하면 어떤 것을 가리키는지 알지 못한다.
            //this대신 getActivity를 사용해서 현 activity를 변환해줄 수 있다.
        });
        return v;
        //RETURN문 뒤에 나오는 statement들은 이미 함수가 return을 통해 결과를 반환했으므로 더이상 진행될 수 없다, unreachable
    }

}