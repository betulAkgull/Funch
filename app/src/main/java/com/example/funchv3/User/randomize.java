package com.example.funchv3.User;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.funchv3.R;
import com.example.funchv3.maps.MapsActivity2;
import com.google.firebase.firestore.FirebaseFirestore;

public class randomize extends AppCompatActivity {
    FirebaseFirestore dbroot;
    TextView restName,cuisine,comment;
    Button address,rating,addFav,randomize2,list,buttonComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);

        restName = findViewById(R.id.restName);
        cuisine = findViewById(R.id.cuisine);
        address = findViewById(R.id.address);
        rating = findViewById(R.id.rating);
        comment = findViewById(R.id.restComm);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(randomize.this, MapsActivity2.class));
            }
        });

        buttonComment = findViewById(R.id.buttonComment);
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(randomize.this, restaurantComments.class));
            }
        });


    }

}
