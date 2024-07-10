package com.example.spongebobvsjellyfish.Screen;

import static com.example.spongebobvsjellyfish.Utilities.SharedPreferencesManager.init;

import android.app.Application;

import com.example.spongebobvsjellyfish.Utilities.SharedPreferencesManager;
import com.example.spongebobvsjellyfish.Utilities.SignalManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init(this);
        SignalManager.init(this);
    }
}