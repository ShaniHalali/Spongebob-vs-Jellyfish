package com.example.spongebobvsjellyfish.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class StartActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton start_BTN_start;
    private ExtendedFloatingActionButton speed_toggle_BTN;
    private SoundPlayer soundPlayer;
    private Context context;
    private boolean isFast = false;
    private boolean fastSpeed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViews();
        initViews();
    }

    private void initViews() {
        start_BTN_start.setOnClickListener(view -> {
            Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
            //  fastSpeed -Intent
            mainIntent.putExtra("EXTRA_FAST_SPEED", fastSpeed);
            startActivity(mainIntent);
            finish();
        });

        speed_toggle_BTN.setOnClickListener(view -> {
            if (isFast) {
                speed_toggle_BTN.setText("ITS SLOW  MOOD");
                fastSpeed=false;
            } else {
                speed_toggle_BTN.setText("ITS  FAST  MOOD");
                fastSpeed=true;
            }
            isFast = !isFast;
        });
    }

    private boolean isFastMood(){
        return fastSpeed;// true or false
    }
    private void findViews() {
        start_BTN_start = findViewById(R.id.start_BTN_start);
        speed_toggle_BTN = findViewById(R.id.speed_toggle_BTN);
    }
}
