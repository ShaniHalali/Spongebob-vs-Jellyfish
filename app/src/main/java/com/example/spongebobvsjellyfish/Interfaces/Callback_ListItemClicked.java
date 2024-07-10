package com.example.spongebobvsjellyfish.Interfaces;


import com.example.spongebobvsjellyfish.Models.Player;

public interface Callback_ListItemClicked {
    void listItemClicked(double lat, double lon,int position);

    void listItemClicked(Player item, int adapterPosition);
}