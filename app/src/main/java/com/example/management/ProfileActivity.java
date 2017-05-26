package com.example.management;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEamil;
    private Button buttonLogout, buttonRegister;
    private ImageView petImage;
    private Button btn_UploadPicture;
    private EditText petName, petAge;
    private RadioButton radioGender_male, radioGender_female, radio_dog, radio_cat;
    private RadioButton radioSize_small, radioSize_middle, radioSize_large;
    private Button btn_profilefinish;
    private RadioGroup radiogroup_gender, radiogroup_type, radiogroup_size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEamil = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEamil.setText( user.getEmail()+"님의 반려동물");
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        petImage = (ImageView) findViewById(R.id.petImage);
        btn_UploadPicture = (Button) findViewById(R.id.btn_UploadPicture);
        petName = (EditText) findViewById(R.id.petName);
        petAge = (EditText) findViewById(R.id.petAge);
        radiogroup_gender = (RadioGroup) findViewById(R.id.radiogroup_gender);
        radioGender_male = (RadioButton) findViewById(R.id.radioGender_male);
        radioGender_female = (RadioButton) findViewById(R.id.radioGender_female);
        radiogroup_type = (RadioGroup) findViewById(R.id.radiogroup_type);
        radio_dog = (RadioButton) findViewById(R.id.radio_dog);
        radio_cat = (RadioButton) findViewById(R.id.radio_cat);
        radiogroup_size = (RadioGroup) findViewById(R.id.radiogroup_size);
        radioSize_small = (RadioButton) findViewById(R.id.radioSize_small);
        radioSize_middle = (RadioButton) findViewById(R.id.radioSize_middle);
        radioSize_large = (RadioButton) findViewById(R.id.radioSize_large);

        btn_profilefinish = (Button) findViewById(R.id.btn_profilefinish);

        btn_profilefinish.setOnClickListener(this);
        loadSavedPreferences();
    }

    public void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pName = sharedPreferences.getString("storedpetname", "yourpetname");
        petName.setText(pName);

        String pAge = sharedPreferences.getString("storedpetage", "yourpetage");
        petAge.setText(pAge);

        Boolean pType = sharedPreferences.getBoolean("storedpettype", Boolean.parseBoolean("yourpettype"));
        if(sharedPreferences.getBoolean("dog", false))
            radio_dog.setChecked(true);
        else
            radio_cat.setChecked(true);

        Boolean pGender = sharedPreferences.getBoolean("storedpetgender", Boolean.parseBoolean("yourpetgender"));
        if(sharedPreferences.getBoolean("male", false))
            radioGender_male.setChecked(true);
        else
            radioGender_female.setChecked(true);

        Boolean pSize = sharedPreferences.getBoolean("storedpetsize", Boolean.parseBoolean("yourpetsize"));
        if(sharedPreferences.getBoolean("small", false))
            radioSize_small.setChecked(true);
        else if(sharedPreferences.getBoolean("middle", false))
            radioSize_middle.setChecked(true);
        else
            radioSize_large.setChecked(true);



    }
    public void savedPreferences(String key, String value){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void savedGenderPreferences(String key, Boolean value){
        //성별 선택
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int selectedGender = radiogroup_gender.getCheckedRadioButtonId();
        if(selectedGender == R.id.radioGender_male)
            editor.putBoolean("male", true);
        else
            editor.putBoolean("male", false);
        editor.commit();
    }
    public void savedTypePreferences(String key, Boolean value){
        //강아지,고양이 선택
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int selectedType = radiogroup_type.getCheckedRadioButtonId();
        if(selectedType == R.id.radio_dog)
            editor.putBoolean("dog", true);
        else
            editor.putBoolean("dog", false);
        editor.commit();
    }
    public void savedSizePreferences(String key, Boolean value){
        //소,중,대 견/묘 선택
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int selectedType = radiogroup_size.getCheckedRadioButtonId();
        if(selectedType == R.id.radioSize_small)
            editor.putBoolean("small", true);
        else{
            editor.putBoolean("small", false);
            if(selectedType == R.id.radioSize_middle)
                editor.putBoolean("middle", true);
            else
                editor.putBoolean("middle", false);
        }
        editor.commit();
    }


    @Override
    public void onClick(View v) {
        if(v == buttonLogout){
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(v==btn_profilefinish){
            Toast.makeText(this, "프로필저장", Toast.LENGTH_SHORT).show();
            savedPreferences("storedpetname", petName.getText().toString());
            //"storedpetname"에 petName에 쓴 글씨 들어가기
            savedPreferences("storedpetage", petAge.getText().toString());
            savedGenderPreferences("storedpetgender", radioGender_male.isChecked());
            savedGenderPreferences("storedpetgender", radioGender_female.isChecked());
            savedTypePreferences("storedpettype", radio_dog.isChecked());
            savedTypePreferences("storedpettype", radio_cat.isChecked());
            savedSizePreferences("storedpetsize", radioSize_small.isChecked());
            savedSizePreferences("storedpetsize", radioSize_middle.isChecked());
            savedSizePreferences("storedpetsize", radioSize_large.isChecked());

        }



//        savedPreferences("storedpetage", petAge.getText().toString());
//        if(v.getId() == R.id.btn_profilefinish){
//            //SharedPreference 환경 변수 사용
//            SharedPreferences prefs = getSharedPreferences("login", 0);
//            //prefs.getString() return값이 null이라면 2번째 함수를 대입한다.
////            String  pet_name = prefs.getString("petss","");
//
//
//        }
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
