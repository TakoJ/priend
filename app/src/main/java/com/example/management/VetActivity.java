package com.example.management;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class VetActivity extends AppCompatActivity {
    ImageButton searchVetButton;
    ImageButton mapButton;
    private final Context context = this;
    MyApplicationPermission mapPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchVetButton = (ImageButton) findViewById(R.id.SearchVetButton);
        mapButton = (ImageButton) findViewById(R.id.mapButton);

        //permission code for android version 6.0
        mapPermission = (MyApplicationPermission) getApplicationContext();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //조건문이 true일 경우, permission허가된 것, if문이 실행된다.
            MyApplicationPermission.mapPermission = true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
        }
        if (!MyApplicationPermission.mapPermission) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }

        searchVetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VetlistActivity.class);
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, mapActivity.class);
                startActivity(intent);
            }
        });
    }
///////////////////////////////////메인에는 검색버튼 눌렀을 때 이벤트///////////////////////////////////////////////////////
    //////////////////////자동완성 정규식 --> 일단 이후에 생각하는 걸로/////////////////////////////


    //permission 설정
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MyApplicationPermission.mapPermission = true;
            } else {
            }
            return;
        }
    }
}