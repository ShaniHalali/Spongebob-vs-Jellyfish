package com.example.spongebobvsjellyfish;


import com.example.spongebobvsjellyfish.Models.SquareEntity;

public interface BoardUpdateListener {
    void onBoardUpdated(SquareEntity[][] board);

    void onChangedLife(int number);

    void onGameLost();

}

