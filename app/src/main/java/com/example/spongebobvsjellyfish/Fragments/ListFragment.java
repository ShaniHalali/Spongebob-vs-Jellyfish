package com.example.spongebobvsjellyfish.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spongebobvsjellyfish.Adapter.PlayerAdapter;
import com.example.spongebobvsjellyfish.Interfaces.Callback_ListItemClicked;
import com.example.spongebobvsjellyfish.Models.Player;
import com.example.spongebobvsjellyfish.Models.PlayerList;
import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Screen.EndActivity;
import com.example.spongebobvsjellyfish.Screen.RecordsActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ListFragment extends Fragment {
    private RecyclerView fragment_list_RCW_records;
    private Callback_ListItemClicked callbackListItemClicked;
    private PlayerAdapter playerAdapter;
    private PlayerList playerList;
    private SharedPreferences sp;
    private ExtendedFloatingActionButton list_BTN_location;

    public ListFragment() {
        // Required empty public constructor
    }

    public void setCallbackListItemClicked(Callback_ListItemClicked callbackListItemClicked) {
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        sp = getActivity().getSharedPreferences("PlayerPrefs", Context.MODE_PRIVATE);

        findViews(v);
        initPlayerList();
        initViews();
        return v;
    }

    private void initPlayerList() {
        playerList = new PlayerList();

        // Read from SharedPreferences
        for (int i = 0; ; i++) {
            String strName = sp.getString("name_" + i, null);
            int intScore = sp.getInt("score_" + i, -1);
            if (strName != null && intScore != -1) {
                playerList.addPlayer(new Player().setName(strName).setScore(intScore));
            } else {
                break; // Exit the loop if no more players are found
            }
        }
        // Sort the players
        playerList.sortPlayersByScore();


    }

    private void initViews() {
        fragment_list_RCW_records.setLayoutManager(new LinearLayoutManager(getContext()));
        playerAdapter = new PlayerAdapter(playerList.getAllPlayers(), callbackListItemClicked);
        fragment_list_RCW_records.setAdapter(playerAdapter);

        // list_BTN_location.setOnClickListener(view -> {


        //  });
    }

    private void findViews(View v) {
        fragment_list_RCW_records = v.findViewById(R.id.list_LST_players);
        // list_BTN_location=v.findViewById(R.id.list_BTN_location);
    }
}