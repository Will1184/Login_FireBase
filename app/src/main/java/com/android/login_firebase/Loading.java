package com.android.login_firebase;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

public class Loading {
    Activity activity;
    AlertDialog dialog;

    public Loading(Activity myActivity) {
        activity = myActivity;
    }
    void  LoadingAnimationDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading,null));
        builder.setCancelable(false);
        dialog=builder.create();
        dialog.show();
    }
    void  dismissLoadingAnimation(){
        dialog.dismiss();
    }
}
