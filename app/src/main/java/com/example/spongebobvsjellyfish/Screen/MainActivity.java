package com.example.spongebobvsjellyfish.Screen;




import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;


import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.BoardUpdateListener;
import com.example.spongebobvsjellyfish.Logic.GameManager;
import com.example.spongebobvsjellyfish.Models.Direction;
import com.example.spongebobvsjellyfish.Models.SquareEntity;
import com.example.spongebobvsjellyfish.Utilities.SignalManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity implements BoardUpdateListener {

    private ExtendedFloatingActionButton main_BTN_left;
    private ExtendedFloatingActionButton main_BTN_right;
    private ShapeableImageView main_IMG_background;
    private ShapeableImageView[][] game_IMG_JellyMatrix;
    private AppCompatImageView[] main_IMG_hearts;
    private GameManager gameManager;
    private final int ROWS=7;
    private final int COLS=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        initGameManager();
        SignalManager.init(this);
    }

    private void initGameManager() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.jellyfish);
                game_IMG_JellyMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }

        gameManager = new GameManager();
        gameManager.setListener(this);
    }

    private void findViews() {
        //!MARK:background
        main_IMG_background = findViewById(R.id.main_IMG_background);
        //MARK:btn
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        //!---------images
        //MARK:hearts
        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart3),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart1)
        };

        //MARK:jellyfish matrix
        game_IMG_JellyMatrix = new ShapeableImageView[][]{
                {findViewById(R.id.game_IMG_jellyPos00), findViewById(R.id.game_IMG_jellyPos01), findViewById(R.id.game_IMG_jellyPos02), findViewById(R.id.game_IMG_jellyPos03), findViewById(R.id.game_IMG_jellyPos04)},
                {findViewById(R.id.game_IMG_jellyPos10), findViewById(R.id.game_IMG_jellyPos11), findViewById(R.id.game_IMG_jellyPos12), findViewById(R.id.game_IMG_jellyPos13), findViewById(R.id.game_IMG_jellyPos14)},
                {findViewById(R.id.game_IMG_jellyPos20), findViewById(R.id.game_IMG_jellyPos21), findViewById(R.id.game_IMG_jellyPos22), findViewById(R.id.game_IMG_jellyPos23), findViewById(R.id.game_IMG_jellyPos24)},
                {findViewById(R.id.game_IMG_jellyPos30), findViewById(R.id.game_IMG_jellyPos31), findViewById(R.id.game_IMG_jellyPos32), findViewById(R.id.game_IMG_jellyPos33), findViewById(R.id.game_IMG_jellyPos34)},
                {findViewById(R.id.game_IMG_jellyPos40), findViewById(R.id.game_IMG_jellyPos41), findViewById(R.id.game_IMG_jellyPos42), findViewById(R.id.game_IMG_jellyPos43), findViewById(R.id.game_IMG_jellyPos44)},
                {findViewById(R.id.game_IMG_jellyPos50), findViewById(R.id.game_IMG_jellyPos51), findViewById(R.id.game_IMG_jellyPos52), findViewById(R.id.game_IMG_jellyPos53), findViewById(R.id.game_IMG_jellyPos54)},
                {findViewById(R.id.game_IMG_jellyPos60), findViewById(R.id.game_IMG_jellyPos61), findViewById(R.id.game_IMG_jellyPos62), findViewById(R.id.game_IMG_jellyPos63), findViewById(R.id.game_IMG_jellyPos64)}

        };
    }

    private void initViews() {
        // MARK: Setting click listeners for player positions
        main_BTN_left.setOnClickListener(view -> gameManager.updateSpongebobPlace(Direction.LEFT));
        main_BTN_right.setOnClickListener(view -> gameManager.updateSpongebobPlace(Direction.RIGHT));
        // MARK:Setting the background image
        main_IMG_background.setImageResource(R.drawable.filed_beckground);

        // MARK:Initializing the hearts visibility
        for (AppCompatImageView heart : main_IMG_hearts) {
            heart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBoardUpdated(SquareEntity[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                switch (board[i][j]) {
                    case EMPTY: {
                        game_IMG_JellyMatrix[i][j].setVisibility(View.INVISIBLE);
                        break;
                    }
                    case JELLY_FISH: {
                        game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.jellyfish);
                        game_IMG_JellyMatrix[i][j].setVisibility(View.VISIBLE);
                        break;
                    }
                    case SPONGE_BOB: {
                        game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.player);
                        game_IMG_JellyMatrix[i][j].setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onChangedLife(int number) {
        for (int i = 1; i <= number; i++) {
            main_IMG_hearts[i - 1].setVisibility(View.VISIBLE);
        }
        for (int i = number + 1; i <= 3; i++) {
            main_IMG_hearts[i - 1].setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onGameLost() {
        Intent endGameIntent = new Intent(MainActivity.this, EndActivity.class);
        startActivity(endGameIntent);
        finish();
    }
}
