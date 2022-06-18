package com.example.funchv3.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.funchv3.R;
import com.example.funchv3.Visitor.homeVisitor;
import com.example.funchv3.Visitor.signup;

public class MainActivity extends AppCompatActivity {
    private Button skipButton;
    private Button signUpButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skipButton = (Button) findViewById(R.id.skip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openHomepage();
            }
        });

        signUpButton = (Button) findViewById(R.id.signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPage();
            }
        });

        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginPage();
            }
        });
    }

    public void openSignUpPage() {
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }

    public void openHomepage() {
        Intent intent = new Intent(MainActivity.this, homeVisitor.class);
        startActivity(intent);
    }

    public void openLoginPage() {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }
}