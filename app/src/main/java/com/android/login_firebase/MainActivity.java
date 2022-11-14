package com.android.login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText sign_in_mail,sign_in_password;
    Button button_Sign_In;
    TextView create_account_txt;
    ValidateInput validateInput;
    String email,password;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    Loading loadingAnimation;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_in_mail=findViewById(R.id.sign_in_email);
        sign_in_password=findViewById(R.id.sign_in_password);

        validateInput = new ValidateInput(MainActivity.this
                ,sign_in_mail
                ,sign_in_password);

        create_account_txt=findViewById(R.id.create_account_txt);
        button_Sign_In=findViewById(R.id.button_sign_in);

        //Firebase Authentication
        mAuth=FirebaseAuth.getInstance();

        loadingAnimation=new Loading(MainActivity.this);
        //Dialog.builder
        builder=new AlertDialog.Builder(this);

        create_account_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }

        });

        button_Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAccount();
            }
        });


    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser mUser=mAuth.getCurrentUser();
        if (mUser!=null){
            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Please log in to continue",Toast.LENGTH_SHORT).show();
        }
    }
    public void signInAccount(){
        loadingAnimation.LoadingAnimationDialog();

        boolean emailVerified= validateInput.validationEmail();
        boolean passwordVerified= validateInput.validationPassword();

        if (emailVerified && passwordVerified) {
            email=sign_in_mail.getText().toString().trim();
            password=sign_in_password.getText().toString().trim();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);
                                loadingAnimation.dismissLoadingAnimation();

                            } else {
                                Toast.makeText(MainActivity.this,"Fatal Error",
                                        Toast.LENGTH_SHORT).show();
                                loadingAnimation.dismissLoadingAnimation();
                            }

                        }
                    });
        }else {
            loadingAnimation.dismissLoadingAnimation();
        }
    }

/*    public void loadingAnimation(){
        LayoutInflater inflater=getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();
    }*/
}