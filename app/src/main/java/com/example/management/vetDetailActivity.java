package com.example.management;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by Chaewon on 2017-05-18.
 */

public class vetDetailActivity extends Activity { //fragment 써야하는 이유?
    View v;
    ImageButton VetAddrMap= (ImageButton) v.findViewById(R.id.VetAddrMap);
    LinearLayout resultView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_detail);

        //get the data from the activity before
        String VetName= getIntent().getStringExtra("VetName");
        String VetAddress= getIntent().getStringExtra("VetAddress");
        String VetTel= getIntent().getStringExtra("VetTel");

        resultView= (LinearLayout) findViewById(R.id.vet_detail);
        //refer the layout for this activity
        alert(VetName, VetAddress, VetTel);
    }

    //activity를 새로 생성하지 않고, dialog를 띄워서 detail 내용을 설명한다.
    private void alert (String name, String addr, String tel){
        new AlertDialog.Builder(this)
                .setTitle(name)
                .setMessage(addr)
                .setMessage(tel)
                .setCancelable(false) //버튼을 누르지 않으면 취소되지 않는 dialog
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                        finish();
                        //확인 버튼이 클릭되면 다이얼로그 종료
                    }
                }).show();

    }

}