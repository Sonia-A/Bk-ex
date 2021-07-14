package com.example.bk_apk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
private EditText username, password;
private Button signupbtn;
private SQLiteDBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.usernamesignup);
        password= findViewById(R.id.passwordsignup);
        myDB = new SQLiteDBHelper(this);

        signupbtn=findViewById(R.id.signupbtn);


        insertUser();
    }

    private void insertUser(){
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean var = myDB.registerUser(username.getText().toString(),password.getText().toString());
                if (var){
                    Toast.makeText(SignUpActivity.this,"User Registered successfully!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this,LoginPage.class));

                }
                else{
                    Toast.makeText(SignUpActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}