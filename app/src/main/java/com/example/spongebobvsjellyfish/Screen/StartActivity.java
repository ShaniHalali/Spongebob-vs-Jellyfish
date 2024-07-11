package com.example.spongebobvsjellyfish.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SwitchCompat;

import com.example.spongebobvsjellyfish.Models.Player;
import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class StartActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton start_BTN_start;
    private ExtendedFloatingActionButton speed_toggle_BTN;
    private SoundPlayer soundPlayer;
    private Context context;
    private boolean isFast = false;
    private boolean fastSpeed = false;
    private SwitchCompat start_slide_fast;
    private SwitchCompat start_slide_sensor;
    private boolean sensorMood=true;
    private AppCompatEditText start_editText_nameInput;

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
            // Pass fastSpeed as an extra to MainActivity
            mainIntent.putExtra("EXTRA_FAST_SPEED", fastSpeed);
            mainIntent.putExtra("EXTRA_SENSOR_MOOD", sensorMood);

            if( isNameEntered()){
                mainIntent.putExtra("EXTRA_PLAYER_NAME", start_editText_nameInput.getText().toString());
                startActivity(mainIntent);
                finish();
            }

            Player player=new Player();
            player.setName(String.valueOf(start_editText_nameInput.getText()));

        });

        //---------- fast mood
        start_slide_fast.setOnCheckedChangeListener((buttonView, isChecked) -> {
            fastSpeed = isChecked;
        });

        // Set fastSpeed to true if the switch is already on when the activity is created
        fastSpeed = start_slide_fast.isChecked();

        //---------- sensor mood
        start_slide_sensor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sensorMood = isChecked;
        });

        // Set fastSpeed to true if the switch is already on when the activity is created
        sensorMood =  start_slide_sensor.isChecked();
    }



    private void findViews() {
        start_BTN_start = findViewById(R.id.start_BTN_start);
        start_slide_sensor = findViewById(R.id.start_slide_sensor);
        start_slide_fast = findViewById(R.id.start_slide_fast);
        start_editText_nameInput=findViewById(R.id.start_editText_nameInput);
    }
    private boolean isNameEntered() {
        if(TextUtils.isEmpty(start_editText_nameInput.getText())){
            start_editText_nameInput.setError("You must enter a name");
            return false;
        }
        return true;
    }
}