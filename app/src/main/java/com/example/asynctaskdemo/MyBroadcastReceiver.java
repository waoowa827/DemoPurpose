package com.example.asynctaskdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String actionString=intent.getAction();
        Toast.makeText(context, actionString +"you selected "+intent.getBooleanExtra("state",false), Toast.LENGTH_LONG).show();


    }
}