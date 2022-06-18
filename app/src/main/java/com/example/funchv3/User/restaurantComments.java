package com.example.funchv3.User;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.funchv3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class restaurantComments extends AppCompatActivity {
    FirebaseFirestore dbroot;
    Button ratecomment,comment2,rating2,list2,usernameButt;
    TextView restName2,userComment,userRating;
    AlertDialog commentdialog;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_comments2);


        comment2 = findViewById(R.id.comment2);
        rating2 = findViewById(R.id.rating2);
        ratecomment = findViewById(R.id.ratecomment);
        restName2 = findViewById(R.id.restName2);

        layout = findViewById(R.id.container);

        buildDialog();
        ratecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                commentdialog.show();
            }
        });
        dbroot = FirebaseFirestore.getInstance();
        DocumentReference document = dbroot.collection("restaurants").document("\n" +
                "LcctLbYWtxNDceJ0bnuI");
        showComments(document);

    }
    //show previous comments
    private void showComments(DocumentReference document) {
        document.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    List<String> cmments = (List<String>) documentSnapshot.get("comment");
                    int commentSize = cmments.size();
                    for(int i =0; i<commentSize; i++){
                        String comm = cmments.get(i);
                        addCard(comm);
                    }

                }
            }
        });
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.commentdialog,null);
        EditText commnt = view.findViewById(R.id.commentDialog);

        builder.setView(view);
        builder.setTitle("Write your comment").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addCard(commnt.getText().toString());
                //save data to firebase
            /*
                  document.update({"comment": (commnt)});
            */
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        commentdialog = builder.create();
    }

    private void addCard(String commnt) {
        View view = getLayoutInflater().inflate(R.layout.card,null);
        TextView commntView = view.findViewById(R.id.commentView);

        commntView.setText(commnt);
        saveComment(commnt);


    }

    private void saveComment(String commnt) {

     //   DocumentReference document = dbroot.collection("restaurants")
          //      .document("LcctLbYWtxNDceJ0bnuI").update("comment",commnt).addOnSuccessListener(new OnSuccessListener<Void>() {




    }
}