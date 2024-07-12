package com.example.spongebobvsjellyfish.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Screen.StartActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class ButtomFragment extends Fragment {

    private ExtendedFloatingActionButton buttom_BTN_menu;

    public ButtomFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttom, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initViews();
    }

    private void findViews(View view) {
        buttom_BTN_menu = view.findViewById(R.id.buttom_BTN_menu);
    }

    private void initViews() {
        buttom_BTN_menu.setOnClickListener(view -> {
            Intent mainIntent = new Intent(getActivity(), StartActivity.class);
            startActivity(mainIntent);
        });
    }
}
