package com.example.management;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class VetActivity extends AppCompatActivity {

    ListView list;
    EditText editsearch;
    String[] vetName;
    String[] vetAddr;
    String[] vetTel;
    ArrayList<VetlistItem> arrayList = new ArrayList<>();
    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet);

        mapButton = (Button) findViewById(R.id.mapButton);

        //generate sample data
        vetName = new String[]{"기린동물병원", "뉴월드동물병원", "다솜종합동물병원", "로하스동물병원", "사과나무동물병원",
                "산들동물병원", "시민동물병원", "아띠동물병원", "애견마을", "엘동물병원", "이용철동물병원", "일산제일동물병원",
                "제연동물병원", "초원동물병원","펫가든", "한사랑동물병원", "한양동물메디컬센터", "LC동물메디컬센터"};

        vetAddr = new String[]{"서울특별시 성동구 마장로 300", "서울특별시 동대문구 천호대로 211",
                "서울특별시 동대문구 전농로 15-3", "경기도 고양시 일산서구 강선로 74 ", "경기도 고양시 일산서구 일산로 539",
                "경기도 고양시 일산동구 고봉로 250", "경기도 고양시 일산서구 대산로 126 문촌프라자",
                "서울특별시 동대문구 사가정로 51", "경기도 고양시 일산동구 일산로 30 효성레제스오피스텔", "서울특별시 동대문구 전농로 64-1"
                , "서울특별시 동대문구 고산자로 391", "경기도 고양시 일산서구 고양대로 692", "경기도 고양시 일산서구 일산동 1065-123",
                "서울특별시 동대문구 답십리로 77", "경기도 고양시 일산서구 일산로 589 현대프라자", "서울특별시 성북구 성북로 27 동방빌딩",
                "서울특별시 성동구 왕십리로 282", "서울특별시 성동구 천호대로 288"};

        vetTel = new String[]{"02-743-7582", "02-2212-5233", "02-2242-7510", "031-924-2125",
                "031-912-7582", "031-976-7585", "031-911-7346", "02-6242-8275", "031-905-0907", "02-972-0023", "02-3295-3927"
                , "031-975-1475", "031-913-7266", "02-2242-7975", "031-911-8766", "02-743-7582", "02-2281-5200",
                "02-3394-7530"};
        list = (ListView) findViewById(R.id.vetList);

        for (int i = 0; i < vetName.length; i++) {
            VetlistItem item = new VetlistItem(vetAddr[i], vetName[i], vetTel[i]);
            //bind all strings into an array
            arrayList.add(item);
        }

        //pass result to adapter class
        final VetlistAdapter adapter = new VetlistAdapter(this, arrayList);

        //bind the adapter to the listview
        list.setAdapter(adapter);


        //Locate the edittext in listview xml
        editsearch = (EditText) findViewById(R.id.listSearch);

        //capture text in edittext
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                if (text.length() > 0) {
                    adapter.filter(text);
                } else {
                    for (int i = 0; i < vetName.length; i++) {
                        VetlistItem item = new VetlistItem(vetAddr[i], vetName[i], vetTel[i]);
                        arrayList.add(item);
                    }
                    final VetlistAdapter initadapter = new VetlistAdapter(getApplicationContext(), arrayList);
                    list.setAdapter(initadapter);
                }
            }
        });

        //permission code for android version 6.0

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //if true, permission allowed. implement the if statement
            MyApplicationPermission.mapPermission = true;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
        }
        if (!MyApplicationPermission.mapPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mapActivity.class);
                intent.putExtra("var", 1);
                startActivity(intent);
            }
        });
    }

    //set the permission
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeButton:
                //homeButton이 눌렸을 경우 이벤트 발생
                startActivity(new Intent(this, MainActivity.class));

            default:
                return super.onOptionsItemSelected(item);

        }

    }

}
