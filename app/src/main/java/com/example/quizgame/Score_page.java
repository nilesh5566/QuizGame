package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Score_page extends AppCompatActivity {
    TextView scoreCorrect, scoreWrong;
    Button playAgain,exit;


    String usercorrect;
    String userwrong;



    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("score");

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);

        scoreCorrect=findViewById(R.id.textViewCurrectAnswer);
        scoreWrong=findViewById(R.id.textViewWrong);
        playAgain=findViewById(R.id.buttonplayAgain);
        exit=findViewById(R.id.buttonExit);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userId=user.getUid();
                usercorrect=snapshot.child(userId).child("correct").getValue().toString();
                userwrong=snapshot.child(userId).child("wrong").getValue().toString();

                scoreCorrect.setText(usercorrect);
                scoreWrong.setText(userwrong);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Score_page.this,MainActivity.class);
                startActivity(intent);
               // finish();


            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });




    }
}