package com.android.login_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {
    ImageView back_arrow_pass;
    EditText update_password,update_repeat_password;
    Button button_new_password;
    String myNewPassword,repeatMyNewPassword;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        back_arrow_pass=findViewById(R.id.back_arrow_pass);
        button_new_password=findViewById(R.id.button_new_password);

        update_password=findViewById(R.id.update_password);
        update_repeat_password=findViewById(R.id.update_repeat_password);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        back_arrow_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdatePasswordActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        button_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myNewPassword=update_password.getText().toString().trim();
                repeatMyNewPassword=update_repeat_password.getText().toString().trim();
                if (repeatMyNewPassword.equals(myNewPassword)){
                    mUser.updatePassword(myNewPassword);
                    Toast.makeText(UpdatePasswordActivity.this, "Password Update Succesfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdatePasswordActivity.this, "Password Dont Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}