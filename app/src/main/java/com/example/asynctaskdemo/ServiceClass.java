package com.example.asynctaskdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.media.MediaPlayer;
import androidx.annotation.Nullable;
import android.provider.Settings;

public class ServiceClass extends Service {

    MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Ana","Hello world");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer=MediaPlayer.create(this,Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Log.d("Ana","intent value:" + intent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
