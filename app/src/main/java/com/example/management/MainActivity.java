package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;


    private Button buttonProfile;
    private Button buttonRegister;
    private Button buttonLogin;
    private Button buttonLogout;
    private Button searching, hospital, community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonProfile = (Button) findViewById(R.id.buttonProfile);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        searching = (Button) findViewById(R.id.searching);
        hospital = (Button) findViewById(R.id.hospital);
        community = (Button) findViewById(R.id.community);

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            //프로필버튼 눌렸을 때
            @Override
            public void onClick(View v) {
                if(v == buttonProfile){
                    if(firebaseAuth.getCurrentUser() == null){
                        //로그인이 안되어있을 시 toast띄우기
                        Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_LONG).show();
                    } else {
                        //로그인상태에만 프로일로 이동
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            //회원가입버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                if(v == buttonRegister){
                    if(firebaseAuth.getCurrentUser() == null){
                        // 로그인이 안되어있을 때만 회원가입으로 이동
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intent);
                    } else {
                        //로그인시에는 toast띄우기
                        Toast.makeText(getApplicationContext(), "회원가입시 로그아웃 상태여야 합니다.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            //로그인버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                if(v == buttonLogin){
                    if(firebaseAuth.getCurrentUser() == null){
                        // 로그인이 안되어있을 때, 로그인창으로 이동
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        //로그인상태시 toast띄우기
                        Toast.makeText(getApplicationContext(), "로그인 상태입니다.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            //로그아웃버튼 눌렀을 때
            @Override
            public void onClick(View v) {
                if(v == buttonLogout){
                    if(firebaseAuth.getCurrentUser() == null){
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    } else {
                        firebaseAuth.signOut();
                        Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        searching.setOnClickListener(new View.OnClickListener(){
            //동물 질병검색 눌렀을 때
            @Override
            public void onClick(View v) {
                if (v == searching) {
                    Intent intent = new Intent(getApplicationContext(), disease_condition.class);
                    startActivity(intent);
                }

            }
        });
        hospital.setOnClickListener(new View.OnClickListener(){
            //동물병원 검색 눌렀을 때
            @Override
            public void onClick(View v) {
                if(v==hospital){
                    Intent intent = new Intent(getApplicationContext(), VetActivity.class);
                    startActivity(intent);
                }

            }
        });
//        community.setOnClickListener(new View.OnClickListener(){
//            //게시판 눌렀을 때
//            @Override
//            public void onClick(View v) {
//                if(v==community){
//                    Intent intent = new Intent(getApplicationContext(),  .class);
//                    startActivity(intent);
//                }
//
//            }
//
//        });
    }
}
