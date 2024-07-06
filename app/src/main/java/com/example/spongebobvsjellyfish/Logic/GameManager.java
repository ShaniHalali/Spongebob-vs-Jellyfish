package com.example.spongebobvsjellyfish.Logic;

import static com.example.spongebobvsjellyfish.Models.SquareEntity.EMPTY;
import static com.example.spongebobvsjellyfish.Models.SquareEntity.JELLY_FISH;
import static com.example.spongebobvsjellyfish.Models.SquareEntity.KRABBY_PATTY;
import static com.example.spongebobvsjellyfish.Models.SquareEntity.SPONGE_BOB;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.spongebobvsjellyfish.BoardUpdateListener;
import com.example.spongebobvsjellyfish.Models.Direction;
import com.example.spongebobvsjellyfish.Models.SquareEntity;
import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.Screen.MainActivity;
import com.example.spongebobvsjellyfish.Utilities.MoveDetector;
import com.example.spongebobvsjellyfish.Utilities.SignalManager;
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;

import java.util.Random;

public class GameManager {

    public static final int ROWS = 7;
    public static final int COLS = 5;
    private  Long TICK_TIMER = 900L;
    private final Random random = new Random();
    private final SquareEntity[][] mBoard = new SquareEntity[ROWS][COLS];
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Object locker = new Object();
    private BoardUpdateListener mListener = null;

    private boolean shouldStop = false;
    private int mLife = 3;

    private SignalManager signalManager;
    private SoundPlayer soundPlayer;
    private Context context;
    private int  mScore=0;
    private boolean isFastMood;
    private boolean fall=false;
    private boolean isSensorMood;
    private MoveDetector moveDetector;
    public GameManager(Context context,boolean isFastMood,boolean isSensorMood) {
        this();
        this.context = context;
        this.isFastMood=isFastMood;
        this.isSensorMood=isSensorMood;
    }

    public GameManager() {
        initBoard();
        startTimer();
    }

    public void setListener(BoardUpdateListener listener) {
        mListener = listener;
        onLifeChanged();
        addScore();
    }

    private void initBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                mBoard[i][j] = EMPTY;
            }
        }
        placeRandomEntitiesOnFirstLine();
        mBoard[ROWS - 1][2] = SPONGE_BOB; // At the middle
    }

    private void placeRandomEntitiesOnFirstLine() {
        //Clear the first line before placing new entities
        for (int i = 0; i < COLS; i++) {
            mBoard[0][i] = EMPTY;
        }
        if(fall){
    // Randomly place at the matrix -Jellyfish or Krabby Patties
    int newFallingIndex = random.nextInt(COLS);
    int entityType = random.nextInt(2); // 0 for Jellyfish, 1 for Krabby Patty, 3 for empty
    if (entityType == 0) {
        mBoard[0][ newFallingIndex ] = JELLY_FISH;
    }else if (entityType == 1) {
        mBoard[0][ newFallingIndex] = KRABBY_PATTY;
    }
        }
      fall=!fall;
    }



    private void startTimer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isFastMood){
                    TICK_TIMER=400L;
                };
                if (!shouldStop) {
                    moveEntities();
                    if(!isFastMood){

                    }
                    handler.postDelayed(this, TICK_TIMER);
                }
            }
        }, TICK_TIMER);
    }

    public void updateSpongebobPlace(Direction direction) {
        synchronized (locker) {
            int fromBobfogIndex = getSpongeBobIndex();
            int toBobsfogIndex = 0;
            //if(!isSensorMood){
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
          //  }else
              {
                //sensorMood on !!!


            }

            }

    }
    private int getJellyfishIndexByRow(int row) {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[row][i] == JELLY_FISH) {
                return i;
            }
        }
        return -1;
    }

    private int getKrabbyPattyIndexByRow(int row) {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[row][i] == KRABBY_PATTY) {
                return i;
            }
        }
        return -1;
    }
    private void onUpdateBoard() {
        if (mListener == null) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(() -> mListener.onBoardUpdated(mBoard));
    }

    public int getSpongeBobIndex() {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[ROWS - 1][i] == SPONGE_BOB) {
                return i;
            }
        }
        return -1;
    }

    private void clearJellyFishFromLastLine() {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[ROWS - 1][i] == JELLY_FISH) {
                mBoard[ROWS - 1][i] = EMPTY;
            }
        }
    }

    private void clearKrabbyFromLastLine() {
        for (int i = 0; i < COLS; i++) {
            if (mBoard[ROWS - 1][i] == KRABBY_PATTY) {
                mBoard[ROWS - 1][i] = EMPTY;
            }
        }
    }

    private void moveEntities() {
        synchronized (locker) {

            // Move Jellyfish
            for (int i = ROWS - 2; i >= 0; i--) {
                int jellyFishIndex = getJellyfishIndexByRow(i);
                if (jellyFishIndex != -1) {
                    boolean hasCrash = false;
                    // Check for crash with SpongeBob
                    if (i == ROWS - 2) {
                        int spongeBobIndex = getSpongeBobIndex();
                        if (jellyFishIndex == spongeBobIndex) {
                            hasCrash = true;
                            handleJellyfishCrash();
                             mBoard[i][jellyFishIndex] = EMPTY;

                            break;
                        }
                    }
                    mBoard[i][jellyFishIndex] = EMPTY;
                    mBoard[i + 1][jellyFishIndex] = JELLY_FISH;
                }
            }

            // Move Krabby Patties
            for (int i = ROWS - 2; i >= 0; i--) {
                int krabbyIndex = getKrabbyPattyIndexByRow(i);
                if (krabbyIndex != -1) {
                    // Check for collection with SpongeBob
                    if (i == ROWS - 2) {
                        int spongeBobIndex = getSpongeBobIndex();
                        if (krabbyIndex == spongeBobIndex) {
                            handleGetKrabbyPatty();
                            mBoard[i][krabbyIndex] = EMPTY; // Remove Krabby Patty after collection
                            continue; // Move to the next entity
                        }
                    }
                    mBoard[i][krabbyIndex] = EMPTY;
                    mBoard[i + 1][krabbyIndex] = KRABBY_PATTY;
                }
            }

            // Place new Jellyfish and Krabby Patties
            placeRandomEntitiesOnFirstLine();
            clearJellyFishFromLastLine();
            clearKrabbyFromLastLine();
            onUpdateBoard();
        }
    }
    private void handleJellyfishCrash() {
        soundPlayer = new SoundPlayer((MainActivity) this.context);
        soundPlayer.playSoundCrash(R.raw.electric);
        SignalManager.getInstance().toast("OUCHHHHH, Be careful!");
        SignalManager.vibrate(100);
        mLife--;
        onLifeChanged();
    }

    private void handleGetKrabbyPatty() {
        soundPlayer = new SoundPlayer((MainActivity) this.context);
        soundPlayer.playSoundCrash(R.raw.krabby);
        SignalManager.getInstance().toast("Yay!");
        SignalManager.vibrate(100);
        // Optional: Increase score or any other game logic

        mScore+=10;
        addScore();
    }

    private void addScore() {
        if (mListener != null) {
            mListener.scoreAdd(mScore);

        }
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