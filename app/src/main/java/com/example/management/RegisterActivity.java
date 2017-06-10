package com.example.management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword, CheckPassword;
    private TextView textViewSignin;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        CheckPassword = (EditText) findViewById(R.id.CheckPassword);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private boolean isValidEmail(String target){
        //email 체크
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private boolean isValidPassword(String target){
        //영어,숫자조합 6~20자 비밀번호
        Pattern p = Pattern.compile( "^[a-zA-Z0-9]{6,20}$");

        Matcher m = p.matcher(target);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkPassword(String target, String check){
        if(target.equals(check)){
            return true;
        }else{
            return false;
        }
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String checkpassword = CheckPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function further
            return;
        }
        if(!isValidEmail(email)){
            //email is wrong
            Toast.makeText(this, "잘못된 이메일입니다.", Toast.LENGTH_SHORT).show();
            //stopping the function further
            return;
        }
        if(!isValidPassword(password)){
            //password is wrong
            Toast.makeText(this, "비밀번호는 6~20자의 영문,숫자 조합으로만 가능합니다.", Toast.LENGTH_LONG).show();
            //stopping the function further
            return;
        }
        if(!checkPassword(password, checkpassword)){
            //확인하는 비밀번호가 같지않다면
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
            //stopping the function further
            return;
        }
        //if validations are ok
        //we will first show a progressbar

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Go to MainActivity
                            startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Could not register, please try again.", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

        });
    }
//    private void onAuthSuccess(FirebaseUser user) {
//        String username = usernameFromEmail(user.getEmail());
//
//        // Write new user
//        writeNewUser(user.getUid(), username, user.getEmail());
//
//        // Go to MainActivity
//        startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
//        finish();
//    }
//
//    private String usernameFromEmail(String email) {
//        if (email.contains("@")) {
//            return email.split("@")[0];
//        } else {
//            return email;
//        }
//    }
//
//    private void writeNewUser(String userId, String name, String email) {
//        User user = new User(name, email);
//
//        mDatabase.child("users").child(userId).setValue(user);
//    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            //will open login activity here
            startActivity(new Intent(this, LoginActivity.class));
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
