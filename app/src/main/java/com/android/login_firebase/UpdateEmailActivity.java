package com.android.login_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateEmailActivity extends AppCompatActivity {
   ImageView back_arrow_email;
   Button button_new_email;
   EditText current_email,new_email;
   TextView text_send_verification_email;

   FirebaseAuth mAuth;
   FirebaseUser mUser;
   ValidateInput validateInput;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        back_arrow_email=findViewById(R.id.back_arrow_email);
        button_new_email=findViewById(R.id.button_new_email);
        new_email=findViewById(R.id.new_email);
        current_email=findViewById(R.id.current_email);
        text_send_verification_email=findViewById(R.id.text_send_verification_email);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        validateInput=new ValidateInput(UpdateEmailActivity.this,new_email);

        setCurrentEmail();

        back_arrow_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_new_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean emailVerified=validateInput.validationEmail();
                String myNewEmail=new_email.getText().toString().trim();
                if (emailVerified && mUser !=null){

                    mUser.updateEmail(myNewEmail);
                    Toast.makeText(UpdateEmailActivity.this,
                            "Email Adress Updated Successfully", Toast.LENGTH_SHORT).show();
                    Handler handler=new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentEmail();
                        }
                    },2000);
                }else{
                    Toast.makeText(UpdateEmailActivity.this,
                            "Invalid Email Adress",Toast.LENGTH_SHORT).show();
                }
            }
        });
        text_send_verification_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser.isEmailVerified()){
                    Toast.makeText(UpdateEmailActivity.this,"Email Alredy Verified",Toast.LENGTH_SHORT).show();

                }else{
                    mUser.sendEmailVerification();
                    Toast.makeText(UpdateEmailActivity.this,
                            "Verification Email Sent!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void setCurrentEmail(){
        if (mUser != null){
            current_email.setEnabled(true);
            current_email.setText(mUser.getEmail());
            current_email.setEnabled(false);
        }else{
            Toast.makeText(this,"Please Login To continue",Toast.LENGTH_SHORT).show();
        }
    }

}