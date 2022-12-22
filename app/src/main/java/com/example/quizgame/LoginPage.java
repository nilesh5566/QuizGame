package com.example.quizgame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPage extends AppCompatActivity {

    EditText mail;
    EditText password;
    Button signIn;
    SignInButton signInGoogle;
    TextView signUp;
    TextView forgetPassword;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    GoogleSignInClient googleSignInClient;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        registerActivityForGoogleSignIn();

        mail=findViewById(R.id.TextEmailAddress);
        password=findViewById(R.id.TextNumberPassword);
        signIn=findViewById(R.id.SignInButton);
        signInGoogle=findViewById(R.id.signInButtonGoogle);
        signUp=findViewById(R.id.textViewSignIn);
        forgetPassword=findViewById(R.id.textViewforgetpassward);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=mail.getText().toString();
                String userPassword=password.getText().toString();


                SignInWithfireBase(userEmail,userPassword);

            }
        });
         signInGoogle.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 signInGoogleNow();

             }
         });
         signUp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(LoginPage.this,SignUpPage.class);
                 startActivity(intent);
                 finish();
             }
         });

         forgetPassword.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               Intent intent=new Intent(LoginPage.this,ForgotPassword.class);
               startActivity(intent);

             }
         });


    }

    public void SignInWithfireBase(String userEmail,String userPassword){
        signIn.setClickable(false);
          auth.signInWithEmailAndPassword(userEmail,userPassword)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {

                         if (task.isSuccessful()){
                             Intent intent=new Intent(LoginPage.this,MainActivity.class);
                             startActivity(intent);
                             finish();
                             Toast.makeText(LoginPage.this, "Sign In is successful", Toast.LENGTH_SHORT).show();
                         }else{
                             Toast.makeText(LoginPage.this, "Sign In is not successful,Please try again", Toast.LENGTH_SHORT).show();
                         }


                      }
                  });

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser user=auth.getCurrentUser();

        if (user!=null){
            Intent intent=new Intent(LoginPage.this,MainActivity.class);
            startActivity(intent);
            finish();

        }


    }
    public void  signInGoogleNow(){
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1089268853367-gm3ttkmsangcnemmlq03tc8hjup2alnn.apps.googleusercontent.com").requestEmail().build();

        googleSignInClient= GoogleSignIn.getClient(this,gso);
        signin();
    }

    public void signin(){
       Intent signIntent= googleSignInClient.getSignInIntent();
       activityResultLauncher.launch(signIntent);

    }
    public void registerActivityForGoogleSignIn(){

        activityResultLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
int  resultcode=result.getResultCode();
Intent data=result.getData();
       if(resultcode==RESULT_OK && data!=null){

            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            firebaseSignWithGoogle(task);

       }

            }

        });

    }
    private void firebaseSignWithGoogle(Task<GoogleSignInAccount> task){
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(LoginPage.this,MainActivity.class);
            startActivity(intent);
            finish();
         firebaseGoogleAccount(account);

        }catch (ApiException e){
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseGoogleAccount(GoogleSignInAccount account){


        AuthCredential authCredential= GoogleAuthProvider.getCredential(account.getIdToken(),null);

        auth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

if (task.isSuccessful()){

FirebaseUser user=auth.getCurrentUser();



}else{



}
            }
        });

    }
}