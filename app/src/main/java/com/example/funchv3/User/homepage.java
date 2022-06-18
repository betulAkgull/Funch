package com.example.funchv3.User;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.funchv3.R;
import com.example.funchv3.maps.MapsActivity2;

public class homepage extends AppCompatActivity {
    private Button list,filter,randomize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        list = findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openListPage();
            }
        });

        filter = findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilterPage();
            }
        });

        randomize = findViewById(R.id.mapicon);
        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepage.this, MapsActivity2.class));
            }
        });
    }

    private void openFilterPage() {
        startActivity(new Intent(homepage.this, filterPage.class));
    }

    private void openListPage() {

        startActivity(new Intent(homepage.this,listPage.class));
    }
}