package com.example.management;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private StorageReference mStorageRef;
    Bitmap bitmap;

    private static final int PICK_FROM_ALBUM = 1;


    private TextView textViewUserEmail;
    private Button buttonLogout;
    ImageView petImage;
    private Button btn_UploadPicture;
    private EditText petName, petAge;
    private RadioButton radioGender_male, radioGender_female, radio_dog, radio_cat;
    private RadioButton radioSize_small, radioSize_middle, radioSize_large;
    private Button btn_profilefinish;
    private RadioGroup radiogroup_gender, radiogroup_type, radiogroup_size;
    private String username;

    String userEmail;
    String userUid;
    String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        SharedPreferences sharedPreferences = getSharedPreferences("email", Context.MODE_PRIVATE);
        userUid = sharedPreferences.getString("uid",user.getUid()); //유저 uid받기
        userEmail = sharedPreferences.getString("email",user.getEmail()); //유저 email(아이디)받기
        username = usernameFromEmail(userEmail);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();


        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        // 이 activity가 켜졌을 때 권한설정 물어보기
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }



        // butterknife: view binder , list adapter 할때도 간편한 코드 구현가능
        petImage = (ImageView) findViewById(R.id.petImage);
        btn_UploadPicture = (Button) findViewById(R.id.btn_UploadPicture);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText( user.getEmail()+"님의 반려동물");
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
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

        btn_UploadPicture.setOnClickListener(this);
        btn_profilefinish.setOnClickListener(this);

        loadSavedPreferences();

 //원래 사진받아오는 자리
            new DownloadImage().execute();



    }


    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction()
    {
        // 앨범호출
       Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //프로필이미지 파이어베이스에 업로드하기
  //파이어베이스에 파일업로드 end

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK)
            Toast.makeText(getApplicationContext(), "onActivityResult:RESULT_NOT_OK", Toast.LENGTH_SHORT).show();

        switch(requestCode){
            case PICK_FROM_ALBUM: {
                try{
                    Uri image = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                        petImage.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }catch(NullPointerException e){
                    Toast.makeText(this, "사진을 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
            new UploadImage().execute();
        }
        if(v==btn_UploadPicture){

            //사진선택 눌렀을 때
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
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
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
    //permission 설정
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private class UploadImage extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
                StorageReference mountainsRef = mStorageRef.child("users").child(userEmail+"jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl(); //오류 suppress함
                        String photoUrl = String.valueOf(downloadUrl);
                        Log.d("url", photoUrl);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users");
                        Hashtable<String, String> profile = new Hashtable<String, String>();

                        profile.put("email", userEmail);
                        profile.put("username", username);
                        profile.put("photo", photoUrl);

                        myRef.child(userUid).setValue(profile);
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String s = dataSnapshot.getValue().toString();
                                Log.d("Profile", s);
                                if(dataSnapshot != null){
                                    Toast.makeText(getApplicationContext(), "사진 업로드가 잘 됐습니다.", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });
            return null;
        }

    }
    private class DownloadImage extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //Upload할때 uid키로 업로드했기 때문에 받아올때도 uid사용
            FirebaseDatabase.getInstance().getReference().child("users").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    try{
                        String value = dataSnapshot.getValue().toString();
                        String petPhoto = dataSnapshot.child("photo").getValue().toString(); //DB에서 사진 가져오기

                        if(TextUtils.isEmpty(petPhoto)){

                        }else{
                            Picasso.with(getApplicationContext()).load(petPhoto).fit().centerInside().into(petImage, new Callback.EmptyCallback() {
                                @Override public void onSuccess() {
                                    // Index 0 is the image view.
                                    Log.d(TAG,"SUCCESS");
                                }
                            });
                        }

                        Log.d(TAG, "Value is: " + value);
                    }catch (NullPointerException e){
                        //사진이 아직없으면 null값 가르키므로 예외처리해주기
                        Toast.makeText(getApplicationContext(),"사진은 아직 없습니다", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(),"예외처리", Toast.LENGTH_SHORT).show();
                    }


                    }



                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
            return null;
        }
    }

}
