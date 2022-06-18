package com.example.funchv3.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.funchv3.R;
import com.google.firebase.auth.FirebaseAuth;

public class listPage extends AppCompatActivity {
    private Button homepage,myprofile,favorites,
            ratings,comments,changepassword,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        homepage = (Button) findViewById(R.id.homepage);
        myprofile = (Button) findViewById(R.id.myprofile);
        favorites = (Button) findViewById(R.id.favorites);
        ratings = (Button) findViewById(R.id.favorites);
        comments = (Button) findViewById(R.id.comments);
        changepassword = (Button) findViewById(R.id.changePass);
        logout = (Button) findViewById(R.id.logout);

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(listPage.this, com.example.funchv3.User.homepage.class));
            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(listPage.this, myProfile.class));
            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(listPage.this, changePassword.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(listPage.this,"Logged out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(listPage.this,MainActivity.class));
            }
        });

    }
}