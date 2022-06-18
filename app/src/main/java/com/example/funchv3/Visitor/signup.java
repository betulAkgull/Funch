package com.example.funchv3.Visitor;

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

import com.example.funchv3.User.MainActivity;
import com.example.funchv3.R;
import com.example.funchv3.User.User;
import com.example.funchv3.User.homepage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText name,surname,email,phoneNo,password,password2;
    private Button register,cancel,addPhoto;
    private ImageView profilePhoto;
    Uri filepath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.editTextTextPersonName);
        surname = (EditText) findViewById(R.id.editTextTextPersonSurname);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);
        phoneNo = (EditText) findViewById(R.id.editTextPhone);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        password2 = (EditText) findViewById(R.id.editTextTextPassword2);
        profilePhoto = findViewById(R.id.profilePhoto);

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openMainActivity();
            }
        });

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });

        addPhoto = (Button) findViewById(R.id.addPhoto);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(signup.this)
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


    }
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                profilePhoto.setImageBitmap(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void openMainActivity() {

        startActivity(new Intent(signup.this, MainActivity.class));
    }

    private void registerUser(){

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference(mAuth.getUid()+new Random().nextInt(50));
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri){

                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference root=db.getReference("Users");

                        root.child(mAuth.getUid()).child("imageUrl").setValue(uri.toString());

                        profilePhoto.setImageBitmap(bitmap);
                        Toast.makeText(getApplicationContext(),"Photo Uploaded",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        String name_user = name.getText().toString().trim();
        String surname_user = surname.getText().toString().trim();
        String email_user = email.getText().toString().trim();
        String phoneNo_user = phoneNo.getText().toString().trim();
        String password_user = password.getText().toString().trim();
        String password2_user = password2.getText().toString().trim();
        String imageUrl = null;

        if(name_user.isEmpty()){
            name.setError("Name is required!");
            name.requestFocus();
            return;
        }
        if(surname_user.isEmpty()){
            surname.setError("Surname is required!");
            surname.requestFocus();
            return;
        }
        if(email_user.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_user).matches()){
            email.setError("Please provide valid email address!");
            email.requestFocus();
            return;
        }
        if(!password_user.isEmpty() && password_user.length()<8){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(password2_user.isEmpty()){
            password2.setError("Password confirmation is required!");
            password2.requestFocus();
            return;
        }

        if(!password_user.toString().equals(password2_user.toString())){
            password2.setError("Does not match with the first password!");
            password2.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email_user,password_user)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(name_user,surname_user,email_user,phoneNo_user,password_user,imageUrl);

                            database.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) //gets id of the registered user
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(signup.this,"User has been registered successfully !",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(signup.this, homepage.class));
                                            }else{
                                                Toast.makeText(signup.this,"Failed to register ! Try Again.",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(signup.this,"Failed to register",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
