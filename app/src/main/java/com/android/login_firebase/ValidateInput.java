package com.android.login_firebase;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateInput {
    private Context context;
    private EditText email,password,repeatPassword;
    String emailInput,passwordInput,repeatPasswordInput;

    ValidateInput(Context myContext, EditText myEmail){
        context = myContext;
        email = myEmail;
    }

    ValidateInput(Context myContex, EditText myEmail, EditText myPassword) {
        context = myContex;
        email = myEmail;
        password = myPassword;
    }
    ValidateInput(Context myContex, EditText myEmail, EditText myPassword, EditText myRepeatPassword) {
        context = myContex;
        email = myEmail;
        password = myPassword;
        repeatPassword=myRepeatPassword;
    }




    boolean validationEmail(){
          emailInput=email.getText().toString().trim();
         if (emailInput.isEmpty()){
             Toast.makeText(context,"Please Enter your Email",
             Toast.LENGTH_SHORT).show();
             return false;
         }
         else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
             Toast.makeText(context,"Invalid Email",
                     Toast.LENGTH_SHORT).show();
             return false;
         }
         else{
             return true;
         }
    }
    boolean validationPassword(){
         passwordInput=password.getText().toString().trim();
        if (passwordInput.isEmpty()){
            Toast.makeText(context,"Please Enter your Password",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(passwordInput.length()<8){
            Toast.makeText(context,"Password Too Short",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    boolean repeatPasswordValidation(){
         repeatPasswordInput=repeatPassword.getText().toString().trim();
         if (repeatPasswordInput.isEmpty()){
             Toast.makeText(context,"Fill out al Fields",
             Toast.LENGTH_SHORT).show();
             return false;
         }else if (!repeatPasswordInput.equals(passwordInput)){
             Toast.makeText(context,"Password dont Match",
                     Toast.LENGTH_SHORT).show();
             return false;
         }else {
             return true;
         }
    }
}
