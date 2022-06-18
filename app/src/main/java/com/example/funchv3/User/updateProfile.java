package com.example.funchv3.User;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.funchv3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class updateProfile extends AppCompatActivity {
    private EditText nameUpdate, surnameUpdate, emailUpdate, phoneUpdate;
    private Button backButton2, cancel2, save, addPhoto2;
    private ImageView profilePhoto2;
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userId;
    Uri filepath;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);



        backButton2 = (Button) findViewById(R.id.backButton2);
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(updateProfile.this, myProfile.class));
            }
        });

        cancel2 = (Button) findViewById(R.id.cancel2);
        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(updateProfile.this,myProfile.class));
            }
        });

        profilePhoto2 = findViewById(R.id.profilePhoto2);
        addPhoto2 = (Button) findViewById(R.id.addPhoto2);
        addPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(updateProfile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });



        nameUpdate = (EditText) findViewById(R.id.nameUpdate);
        surnameUpdate = (EditText) findViewById(R.id.surnameUpdate);
        emailUpdate = (EditText) findViewById(R.id.emailUpdate);
        phoneUpdate = (EditText) findViewById(R.id.phoneUpdate);


        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        dbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if(userprofile!= null){
                    String name_db = userprofile.getName();
                    String surname_db = userprofile.getSurname();
                    String email_db = userprofile.getEmail();
                    String phone_db = userprofile.getPhoneNo();
                    String img_db = userprofile.getImageUrl();

                    Glide.with(updateProfile.this).load(img_db).into(profilePhoto2);
                    nameUpdate.setText(name_db);
                    surnameUpdate.setText(surname_db);
                    phoneUpdate.setText(phone_db);
                    emailUpdate.setText(email_db);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateProfile.this, "Failed to read", Toast.LENGTH_SHORT).show();
            }
        });

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateUser();

            }
        });
    }

    private void updateUser() {

        String name_user = nameUpdate.getText().toString().trim();
        String surname_user = surnameUpdate.getText().toString().trim();
        String email_user = emailUpdate.getText().toString().trim();
        String phoneNo_user = phoneUpdate.getText().toString().trim();
        if(name_user.isEmpty()){
            nameUpdate.setError("Name is required!");
            nameUpdate.requestFocus();
            return;
        }
        if(surname_user.isEmpty()){
            surnameUpdate.setError("Surname is required!");
            surnameUpdate.requestFocus();
            return;
        }
        if(email_user.isEmpty()){
            emailUpdate.setError("Email is required!");
            emailUpdate.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_user).matches()){
            emailUpdate.setError("Please provide valid email address!");
            emailUpdate.requestFocus();
            return;
        }

        dbRef.child(userId).child("name").setValue(name_user);
        dbRef.child(userId).child("surname").setValue(surname_user);
        dbRef.child(userId).child("email").setValue(email_user);
        user.updateEmail(email_user);

        dbRef.child(userId).child("phoneNo").setValue(phoneNo_user);

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference(userId+new Random().nextInt(50));
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri){

                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference root=db.getReference("users");

                        dbRef.child(userId).child("imageUrl").setValue(uri.toString());

                        profilePhoto2.setImageBitmap(bitmap);
                        Toast.makeText(getApplicationContext(),"Photo Uploaded",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Toast.makeText(updateProfile.this,"Profile Updated Successfully",Toast.LENGTH_SHORT).show();

    }

    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                profilePhoto2.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}