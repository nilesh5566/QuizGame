package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz_page extends AppCompatActivity {

    TextView time,currect,wrong;
    TextView question,a,b,c,d;
    Button next,finish;

    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference().child("Question");

    FirebaseAuth auth=FirebaseAuth.getInstance();
FirebaseUser user=auth.getCurrentUser();
DatabaseReference databaseReference=database.getReference();


    String quizQuestion;
    String quizAnswerA;
    String quizAnswerB;
    String quizAnswerC;
    String quizAnswerD;
    String quizcurrectAnswer;
    int questionCount;
    int questionNumber=1;
    String userAnswer;

    int userCorrect=0;
    int userwrong=0;
    
    CountDownTimer countDownTimer;
    private static final long TIME_TOTAL=25000;
    Boolean timeContinue;
    long leftTime=TIME_TOTAL;


    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        time=findViewById(R.id.textViewTime);
        currect=findViewById(R.id.textViewCorrect);
        wrong=findViewById(R.id.textViewwrong);
        question=findViewById(R.id.textViewQuestion);
        a=findViewById(R.id.textViewA);
        b=findViewById(R.id.textViewB);
        c=findViewById(R.id.textViewC);
        d=findViewById(R.id.textViewD);
        next=findViewById(R.id.buttonNext);
        finish=findViewById(R.id.buttonQuit);
        game();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            game();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendScore();
                Intent intent=new Intent(Quiz_page.this,Score_page.class);
                startActivity(intent);
                finish();
            }
        });


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer="a";
                if (quizcurrectAnswer.equals(userAnswer)){
                  a.setBackgroundColor(Color.GREEN);
                  userCorrect++;
                  currect.setText(""+userCorrect);
                }
                else{
                    a.setBackgroundColor(Color.RED);
                    userwrong++;
                    wrong.setText(""+userwrong);
                    findAnswer();
                }

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer="b";

                if (quizcurrectAnswer.equals(userAnswer)){
                    b.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    currect.setText(""+userCorrect);
                }
                else{
                    b.setBackgroundColor(Color.RED);
                    userwrong++;
                    wrong.setText(""+userwrong);
                    findAnswer();
                }
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAnswer="c";
                pauseTimer();

                if (quizcurrectAnswer.equals(userAnswer)){
                    c.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    currect.setText(""+userCorrect);
                }
                else{
                    c.setBackgroundColor(Color.RED);
                    userwrong++;
                    wrong.setText(""+userwrong);
                    findAnswer();
                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer="d";

                if (quizcurrectAnswer.equals(userAnswer)){
                    d.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                    currect.setText(""+userCorrect);
                }
                else{
                    d.setBackgroundColor(Color.RED);
                    userwrong++;
                    wrong.setText(""+userwrong);
                    findAnswer();
                }
            }
        });



    }
    public void game(){
starttimer();


        a.setBackgroundColor(Color.WHITE);
        b.setBackgroundColor(Color.WHITE);
        c.setBackgroundColor(Color.WHITE);
        d.setBackgroundColor(Color.WHITE);

// Read from the database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                questionCount=(int) dataSnapshot.getChildrenCount();



            quizQuestion=dataSnapshot.child(String.valueOf(questionNumber)).child("q").getValue().toString();
                quizAnswerA=dataSnapshot.child(String.valueOf(questionNumber)).child("a").getValue().toString();
                quizAnswerB=dataSnapshot.child(String.valueOf(questionNumber)).child("b").getValue().toString();
                quizAnswerC=dataSnapshot.child(String.valueOf(questionNumber)).child("c").getValue().toString();
                quizAnswerD=dataSnapshot.child(String.valueOf(questionNumber)).child("d").getValue().toString();
                quizcurrectAnswer=dataSnapshot.child(String.valueOf(questionNumber)).child("ans").getValue().toString();


                question.setText(quizQuestion);
                a.setText(quizAnswerA);
                b.setText(quizAnswerB);
                c.setText(quizAnswerC);
                d.setText(quizAnswerD);

                if (questionNumber<questionCount){
                    questionNumber++;
                }
                else{
                    Toast.makeText(Quiz_page.this, "You answered all Question ", Toast.LENGTH_SHORT).show();
                }


       }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Quiz_page.this, "There is an error "
                        , Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void findAnswer(){
        if (quizcurrectAnswer.equals("a")){
            a.setBackgroundColor(Color.GREEN);
        }
        else  if (quizcurrectAnswer.equals("b")){
            b.setBackgroundColor(Color.GREEN);
        }
        else  if (quizcurrectAnswer.equals("c")){
            c.setBackgroundColor(Color.GREEN);
        }
        else  if (quizcurrectAnswer.equals("d")){
            d.setBackgroundColor(Color.GREEN);
        }
    }

    public void starttimer(){
        countDownTimer=new CountDownTimer(leftTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                leftTime=millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
   timeContinue=false;
   pauseTimer();
   question.setText("Sorry ,Time is up !");
            }
        }.start();
        timeContinue=true;
    }

    public void resetTimer(){
        leftTime=TIME_TOTAL;
        updateCountDownText();
    }


    public void updateCountDownText(){
        int second=(int) (leftTime/1000)%60;
        time.setText(""+second);

    }
    public void pauseTimer(){
        countDownTimer.cancel();
        timeContinue=false;
    }

    public void sendScore() {
        String userID = user.getUid();
        databaseReference.child("Score").child(userID).child("currect").setValue(userCorrect).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            Toast.makeText(Quiz_page.this,"Score send Succerssfully",Toast.LENGTH_LONG).show();
            }
        });
        databaseReference.child("Score").child(userID).child("wrong").setValue(userwrong).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Quiz_page.this,"Score send Succerssfully",Toast.LENGTH_LONG).show();

            }
        });


        }}