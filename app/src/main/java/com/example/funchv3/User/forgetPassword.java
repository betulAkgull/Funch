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
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText verifEmail;
    private String email;
    private Button sendMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();
        verifEmail = findViewById(R.id.verifEmail);
        sendMail = findViewById(R.id.sendMail);

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valideData();
            }
        });


    }

    private void valideData() {
        email = verifEmail.getText().toString();
        if(email.isEmpty()){
            verifEmail.setError("Enter your email address !");
            verifEmail.requestFocus();
        }else{
            forgetPass();
        }
    }

    private void forgetPass() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgetPassword.this, "Password recover mail has been sent, please check your email. ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(forgetPassword.this, login.class));

                }else{
                    Toast.makeText(forgetPassword.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}