package com.example.funchv3.Visitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.funchv3.User.MainActivity;
import com.example.funchv3.R;

public class homeVisitor extends AppCompatActivity {
    private Button backButton3,randomize3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visitor);

        backButton3 = findViewById(R.id.backButton3);
        backButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeVisitor.this, MainActivity.class));
            }
        });

        randomize3 = findViewById(R.id.randomize3);
        randomize3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(homeVisitor.this,"Please Login or Signin !",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(homeVisitor.this,MainActivity.class));
            }
        });
    }
}