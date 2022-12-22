package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
EditText mail;
Button button;
ProgressBar progressBarvi;

FirebaseAuth auth=FirebaseAuth.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mail=findViewById(R.id.editextEmail);
        button=findViewById(R.id.buttoncontinue);
        progressBarvi=findViewById(R.id.forgotprograssBar);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=mail.getText().toString();
                resetPassword(userid);
            }
        });
    }
    public void resetPassword(String userEmail){
            progressBarvi.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPassword.this, "We send you an email to reset your password", Toast.LENGTH_LONG).show();
                        button.setClickable(false);
                        progressBarvi.setVisibility(View.INVISIBLE);
                finish();
                    }else{
                        Toast.makeText(ForgotPassword.this, "Sorry ther is a problem ,please try again later....", Toast.LENGTH_LONG).show();
                    }

                }
            });
    }
}