package com.example.funchv3.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.funchv3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button login,forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.login);
        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, forgetPassword.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {

        String emailUser = email.getText().toString().trim();
        String passUser = password.getText().toString().trim();

        if(emailUser.isEmpty()){
            email.setError("Email field cannot be empty");
            email.requestFocus();
            return;
        }
        if(passUser.isEmpty()){
            password.setError("Password field cannot be empty");
        }
        else{
            mAuth.signInWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(login.this,"Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(com.example.funchv3.User.login.this, homepage.class));
                    }else{
                        Toast.makeText(login.this,"Login failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
