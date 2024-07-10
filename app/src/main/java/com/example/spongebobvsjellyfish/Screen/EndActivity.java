package com.example.spongebobvsjellyfish.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class EndActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton end_BTN_again;
    private ExtendedFloatingActionButton end_BTN_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        findViews();
        initViews();

    }
    private void findViews() {
        end_BTN_again = findViewById(R.id.end_BTN_again);
        end_BTN_records = findViewById(R.id.end_BTN_records);
    }
    private void initViews() {
        end_BTN_again.setOnClickListener(view -> {
            Intent mainIntent = new Intent(EndActivity.this, StartActivity.class);
            startActivity(mainIntent);
            finish();
        });
        end_BTN_records.setOnClickListener(view -> {
            Toast.makeText(EndActivity.this, "Records Button Clicked", Toast.LENGTH_SHORT).show();
            Intent recordsIntent = new Intent(EndActivity.this, RecordsActivity.class);
            startActivity(recordsIntent);
            finish();
        });
    }


}
