package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView signOut;

    Button start;

    FirebaseAuth auth=FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signOut=findViewById(R.id.textViewsignOut);
        start=findViewById(R.id.buttonStart);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

          auth.signOut();
                Intent intent=new Intent(MainActivity.this,LoginPage.class);
                startActivity(intent);
                finish();

            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Quiz_page.class);
                startActivity(intent);
            }
        });

    }
}