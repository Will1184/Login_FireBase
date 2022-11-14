package com.android.login_firebase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    Button button_logOut;
    Button button_update_email;
    Button button_update_password;
    TextView text_id;
    TextView text_email,text_verified_account;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String myEmail,myId;


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure want to log out?");
        AlertDialog dialog=builder.create();
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                mAuth.signOut();
                finish();
                Toast.makeText(HomeActivity.this,"Please log in to continue",
                        Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button_logOut=findViewById(R.id.button_logOut);
        button_update_email=findViewById(R.id.button_update_email);
        button_update_password=findViewById(R.id.button_update_password);
        text_email=findViewById(R.id.text_email);
        text_id=findViewById(R.id.text_id);
        text_verified_account=findViewById(R.id.text_verified_account);

        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        if (mUser!=null){
            myEmail=mUser.getEmail();
            myId=mUser.getUid();

            text_id.setText(myId);
            text_email.setText(myEmail);

            checkAccountVerification();
        }

        button_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                Toast.makeText(HomeActivity.this,"Please log in to continue",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        button_update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, UpdateEmailActivity.class);
                startActivity(intent);
            }
        });

        button_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        if (mUser != null){
            myEmail=mUser.getEmail();
            myId =mUser.getUid();

            text_email.setText(myEmail);
            text_id.setText(myId);
        }
    }
    public void checkAccountVerification(){
        boolean verification =mUser.isEmailVerified();
        if (verification){
            text_verified_account.setText("Account Verified");
            text_verified_account.setTextColor(getResources().getColor(R.color.green));
        }
    }
}