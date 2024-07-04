package com.example.spongebobvsjellyfish.Screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spongebobvsjellyfish.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class StartActivity extends AppCompatActivity {
private ExtendedFloatingActionButton start_BTN_start;
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
            startActivity(mainIntent);
            finish();
        });
    }

    private void findViews() {
        start_BTN_start = findViewById(R.id.start_BTN_start);
    }
}