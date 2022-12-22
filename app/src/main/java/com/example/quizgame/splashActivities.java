package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashActivities extends AppCompatActivity {

    ImageView imageView;
    TextView  title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activities);
        imageView=findViewById(R.id.splashimage);
        title=findViewById(R.id.splashTextView);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splashanam);
     title.startAnimation(animation);

     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

             Intent intent=new Intent(splashActivities.this,LoginPage.class);
                startActivity(intent);
                finish();

         }
     },5000);

    }
}