package com.example.spongebobvsjellyfish.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class EndActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton end_BTN_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        findViews();
        initViews();

    }

    private void initViews() {
        end_BTN_again.setOnClickListener(view -> {
            Intent mainIntent = new Intent(EndActivity.this, StartActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }

    private void findViews() {
        end_BTN_again = findViewById(R.id.end_BTN_again);
    }
}
