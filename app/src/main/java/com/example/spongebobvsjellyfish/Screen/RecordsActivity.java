package com.example.spongebobvsjellyfish.Screen;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.spongebobvsjellyfish.Fragments.ListFragment;
import com.example.spongebobvsjellyfish.Fragments.MapFragment;
import com.example.spongebobvsjellyfish.Interfaces.Callback_ListItemClicked;
import com.example.spongebobvsjellyfish.Models.Player;
import com.example.spongebobvsjellyfish.R;
import com.google.android.material.imageview.ShapeableImageView;

public class RecordsActivity extends AppCompatActivity {
    private static final String TAG = "RecordsActivity";
    private ShapeableImageView records_table_IMG_background;
    private FrameLayout main_FRAME_list;
    private FrameLayout main_FRAME_map;

    private ListFragment listFragment;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Log.d(TAG, "onCreate: Started");

        findViews();
        initViews();
        initializeFragments();
    }

    private void findViews() {
        main_FRAME_list = findViewById(R.id.main_FRAME_list);
        main_FRAME_map = findViewById(R.id.main_FRAME_map);
    }

    private void initViews() {
       listFragment = new ListFragment();
       mapFragment = new MapFragment();

       //set callback
        listFragment.setCallbackListItemClicked(new Callback_ListItemClicked() {
            @Override
            public void listItemClicked(Player player) {
                mapFragment.zoom(player.getLatitude(), player.getLongitude());
            }
        });

    }

    private void initializeFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_FRAME_list, listFragment);
        transaction.replace(R.id.main_FRAME_map, mapFragment);
        transaction.commit();
    }
}
