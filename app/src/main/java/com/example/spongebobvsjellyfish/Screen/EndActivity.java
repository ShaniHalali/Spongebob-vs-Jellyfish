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