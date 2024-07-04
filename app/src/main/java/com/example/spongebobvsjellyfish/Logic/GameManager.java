package com.example.spongebobvsjellyfish.Logic;



import static com.example.spongebobvsjellyfish.Models.SquareEntity.EMPTY;
import static com.example.spongebobvsjellyfish.Models.SquareEntity.JELLY_FISH;
import static com.example.spongebobvsjellyfish.Models.SquareEntity.SPONGE_BOB;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;


import com.example.spongebobvsjellyfish.BoardUpdateListener;
import com.example.spongebobvsjellyfish.Models.Direction;
import com.example.spongebobvsjellyfish.Models.SquareEntity;
import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Screen.MainActivity;
import com.example.spongebobvsjellyfish.Utilities.SignalManager;
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;

import java.util.Random;

public class GameManager {

    public static final int ROWS = 7;
    public static final int COLS = 5;
    private final Long TICK_TIMER = 1000L;
    private final Random random = new Random();
    private final SquareEntity[][] mBoard = new SquareEntity[ROWS][COLS];
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Object locker = new Object();
    private BoardUpdateListener mListener = null;

    private boolean shouldStop = false;
    private int mLife = 3;
    private SignalManager signalManager;
   private  SoundPlayer soundPlayer;
    private Context context;


    public GameManager(Context context) {
        this();
        this.context=context;

;    }

    public GameManager() {
        initBoard();
        startTimer();
    }



    public void setListener(BoardUpdateListener listener) {
        mListener = listener;
        onLifeChanged();
    }


    private void initBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                mBoard[i][j] = EMPTY;
            }
        }
        placeRandomJellyFishOnFirstLine();
        mBoard[ROWS-1][2] = SPONGE_BOB; //AT THE MIDDLE
    }

    private void placeRandomJellyFishOnFirstLine() {
        int jellyFishIndex = random.nextInt(COLS);
        mBoard[0][jellyFishIndex] = JELLY_FISH;
    }

    private void clearJellyFishFromLastLine() {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[ROWS - 1][i] == JELLY_FISH) {
                mBoard[ROWS - 1][i] = EMPTY;
            }
        }
    }


    private void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!shouldStop) {
                    moveJellyFish();
                    handler.postDelayed(this, TICK_TIMER);
                }
            }
        }, TICK_TIMER);
    }


    public void updateSpongebobPlace(Direction direction) {
        synchronized (locker) {
            int fromBobfogIndex = getSpongeBobIndex();
            int toBobsfogIndex = 0;
            switch (direction) {
                case LEFT: {
                    if (fromBobfogIndex == 0) {
                        return;
                    }
                    toBobsfogIndex = fromBobfogIndex - 1;
                    break;
                }
                case RIGHT: {
                    if (fromBobfogIndex == COLS - 1) {
                        return;
                    }
                    toBobsfogIndex = fromBobfogIndex + 1;
                    break;
                }
            }
            mBoard[ROWS - 1][fromBobfogIndex] = EMPTY;
            mBoard[ROWS - 1][toBobsfogIndex] = SPONGE_BOB;
            onUpdateBoard();
        }
    }

    private void onUpdateBoard() {
        if (mListener == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(() -> mListener.onBoardUpdated(mBoard));
    }

    private int getSpongeBobIndex() {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[ROWS-1][i] == SPONGE_BOB) {
                return i;
            }
        }
        return -1;
    }

    private int getJellyfishIndexByRow(int row) {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[row][i] == JELLY_FISH) {
                return i;
            }
        }
        return -1;
    }

//MARK:ACTIVE MATRIX
    private void moveJellyFish( ) {
        synchronized (locker) {
            //5 4 3 2 1 0
            for (int i = ROWS - 2; i >= 0; i--) {

                int jellyFishIndex = getJellyfishIndexByRow(i);
                if (jellyFishIndex == -1) {
                    continue;
                }
                //For the dangerous row
                boolean hasCrash = false;
                if (i == ROWS - 2) {
                    int spongeBoBIndex = getSpongeBobIndex();
                    if (jellyFishIndex == spongeBoBIndex) {
                        hasCrash = true;
                        soundPlayer = new SoundPlayer((MainActivity) this.context);
                        soundPlayer.playSoundCrash(R.raw.electric);
                        SignalManager.getInstance().toast("OUCHHHHH, Be careful! ");
                        SignalManager.vibrate(500);

                        mLife--;
                        onLifeChanged();
                        if (mLife == 0) {
                            return;
                        }
                    }
                }

                mBoard[i][jellyFishIndex] = EMPTY;
                if (!hasCrash) {
                    mBoard[i + 1][jellyFishIndex] = JELLY_FISH;
                }
            }
        }
        placeRandomJellyFishOnFirstLine();
        clearJellyFishFromLastLine();
        onUpdateBoard();

    }






    private void onLifeChanged() {
        if (mListener != null) {
            mListener.onChangedLife(mLife);
            if (mLife == 0) {
                shouldStop = true;
                mListener.onGameLost();
            }
        }
    }
}

