package com.example.bk_apk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
private EditText username, password;
private Button login, signup;

private SQLiteDBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username = findViewById(R.id.usernamelogin);
        password = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.loginbtn);
        signup = findViewById(R.id.signuplog);

        myDB = new SQLiteDBHelper(this);

        loginUSer();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }


    private void loginUSer() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean var =myDB.checkUser(username.getText().toString(), password.getText().toString());

                if (var){
                    Toast.makeText(LoginPage.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginPage.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(LoginPage.this,"Login in Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}