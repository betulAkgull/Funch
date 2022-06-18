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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePassword extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private EditText newPass,newPass2;
    private Button saveChanges,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        newPass = (EditText) findViewById(R.id.newPassword);
        newPass2 = (EditText) findViewById(R.id.newPassword2);
        saveChanges = (Button) findViewById(R.id.saveChanges);
        cancel = (Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(changePassword.this, listPage.class));
            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserPassword();
            }
        });
    }

    private void changeUserPassword() {


        String newPassUser = newPass.getText().toString().trim();
        String newPassU2 = newPass2.getText().toString().trim();

        if(newPassUser.equals(newPassU2)){
            user.updatePassword(newPassUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(changePassword.this,"Password Changed Successfully!",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(changePassword.this,"Password Change has Failed, Try Again !",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(changePassword.this, "Passwords are not matching!", Toast.LENGTH_SHORT).show();
        }

    }
}