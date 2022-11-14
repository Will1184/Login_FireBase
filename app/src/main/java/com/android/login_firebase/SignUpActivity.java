package com.android.login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    ImageView back_arrow;
    Button button_sign_up;
    EditText sign_up_email,sign_up_password,repeat_password;
    ValidateInput validateInput;
    String password,email;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_up_email=findViewById(R.id.sign_up_email);
        sign_up_password=findViewById(R.id.sign_up_password);
        repeat_password=findViewById(R.id.repeat_password);

        validateInput =new ValidateInput(SignUpActivity.this,
                sign_up_email,sign_up_password,
                repeat_password);

        mAuth = FirebaseAuth.getInstance();

        builder=new AlertDialog.Builder(this);

        button_sign_up=findViewById(R.id.button_sign_up);
        back_arrow=findViewById(R.id.back_arrow);


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                finish();
            }
        });

        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               signUpNewAccount();

            }
        });
    }
    public void signUpNewAccount(){

        loadingAnimation();
        boolean emailVerified= validateInput.validationEmail();
        boolean passwordVerified= validateInput.validationPassword();
        boolean repeatPasswordVerified= validateInput.repeatPasswordValidation();
        if (emailVerified && passwordVerified && repeatPasswordVerified){
            email=sign_up_email.getText().toString().trim();
            password=sign_up_password.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent=new Intent(SignUpActivity.this,HomeActivity.class);
                                startActivity(intent);
                                dialog.dismiss();

                            } else {
                                Toast.makeText(SignUpActivity.this,"Fatal Error",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
        }else {
            dialog.dismiss();
        }

    }

    public void loadingAnimation(){
        LayoutInflater inflater=getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();
    }
}