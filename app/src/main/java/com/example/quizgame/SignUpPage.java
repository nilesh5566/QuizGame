package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPage extends AppCompatActivity {

    EditText Email;
    EditText passward;
    Button signup;
    ProgressBar progressBar;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_google);

     Email=findViewById(R.id.editTextTextEmailSignUp);
      passward=findViewById(R.id.editTextNumberPasswordSignUp);
     signup=findViewById(R.id.buttonfinalSignUp);
     progressBar=findViewById(R.id.progressBarview);
 signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setClickable(false);
                String  userEmail=Email.getText().toString();
                String userPassword=passward.getText().toString();
             signupfirebase(userEmail,userPassword);


            }
        });

   }
    public void signupfirebase(String userEmail,String userPassword){
            progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                finish();
                                Toast.makeText(SignUpPage.this, "Your account is created", Toast.LENGTH_LONG).show();

                                progressBar.setVisibility(View.INVISIBLE);
                            }
                            else{
                                Toast.makeText(SignUpPage.this, "There is a problem, please try again later", Toast.LENGTH_LONG).show();

                            }

                        }
                    });
    }

}