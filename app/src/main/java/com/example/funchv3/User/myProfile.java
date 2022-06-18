package com.example.funchv3.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.funchv3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myProfile extends AppCompatActivity {
    private Button backButton,editprofile;
    private TextView name,surname;
    private FirebaseUser user;
    private DatabaseReference dbref;
    private String userId;
    private ImageView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);


        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myProfile.this, listPage.class));
            }
        });

        editprofile = (Button) findViewById(R.id.editprofile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myProfile.this, updateProfile.class));
            }
        });

        name = (TextView) findViewById(R.id.name);
        surname = (TextView) findViewById(R.id.surname);
        profilePic = (ImageView) findViewById(R.id.profilePic);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbref = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        dbref.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if(userprofile!= null){
                    String name_db = userprofile.getName();
                    String surname_db = userprofile.getSurname();
                    String profilepic = userprofile.getImageUrl();


                    Glide.with(myProfile.this).load(profilepic).into(profilePic);
                    name.setText(name_db);
                    surname.setText(surname_db);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(myProfile.this, "Failed to read", Toast.LENGTH_SHORT).show();
            }
        });



    }
}