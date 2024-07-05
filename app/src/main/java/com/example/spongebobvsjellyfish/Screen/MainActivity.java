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
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity implements BoardUpdateListener {

    private ExtendedFloatingActionButton main_BTN_left;
    private ExtendedFloatingActionButton main_BTN_right;
    private ShapeableImageView main_IMG_background;
    private ShapeableImageView[][] game_IMG_JellyMatrix;
    private AppCompatImageView[] main_IMG_hearts;
    private GameManager gameManager;
    private final int ROWS=7;
    private final int COLS=5;
    private SoundPlayer soundPlayer;
    private ShapeableImageView[][] game_IMG_krabbyMatrix;
    private MaterialTextView game_LBL_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initViews();
        // קבל את הערך של fastSpeed מה-Intent
        boolean isFastMood = getIntent().getBooleanExtra("EXTRA_FAST_SPEED", false);

        // השתמש ב-isFastMood כדי לאתחל את ה-GameManager
        initGameManager(isFastMood);
        SignalManager.init(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopSoundGame(R.raw.game);
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlayer=new SoundPlayer(this);
        soundPlayer.playSoundGame(R.raw.game);
    }

    private void initGameManager(boolean isFastMood) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.jellyfish);
                game_IMG_JellyMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }

        gameManager = new GameManager(this,isFastMood);
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
        // MARK:score
        game_LBL_score=findViewById(R.id. game_LBL_score);

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

        game_IMG_krabbyMatrix = new ShapeableImageView[][]{
                {findViewById(R.id.game_IMG_krabbyPos00), findViewById(R.id.game_IMG_krabbyPos01), findViewById(R.id.game_IMG_krabbyPos02), findViewById(R.id.game_IMG_krabbyPos03), findViewById(R.id.game_IMG_krabbyPos04)},
                {findViewById(R.id.game_IMG_krabbyPos10), findViewById(R.id.game_IMG_krabbyPos11), findViewById(R.id.game_IMG_krabbyPos12), findViewById(R.id.game_IMG_krabbyPos13), findViewById(R.id.game_IMG_krabbyPos14)},
                {findViewById(R.id.game_IMG_krabbyPos20), findViewById(R.id.game_IMG_krabbyPos21), findViewById(R.id.game_IMG_krabbyPos22), findViewById(R.id.game_IMG_krabbyPos23), findViewById(R.id.game_IMG_krabbyPos24)},
                {findViewById(R.id.game_IMG_krabbyPos30), findViewById(R.id.game_IMG_krabbyPos31), findViewById(R.id.game_IMG_krabbyPos32), findViewById(R.id.game_IMG_krabbyPos33), findViewById(R.id.game_IMG_krabbyPos34)},
                {findViewById(R.id.game_IMG_krabbyPos40), findViewById(R.id.game_IMG_krabbyPos41), findViewById(R.id.game_IMG_krabbyPos42), findViewById(R.id.game_IMG_krabbyPos43), findViewById(R.id.game_IMG_krabbyPos44)},
                {findViewById(R.id.game_IMG_krabbyPos50), findViewById(R.id.game_IMG_krabbyPos51), findViewById(R.id.game_IMG_krabbyPos52), findViewById(R.id.game_IMG_krabbyPos53), findViewById(R.id.game_IMG_krabbyPos54)},
                {findViewById(R.id.game_IMG_krabbyPos60), findViewById(R.id.game_IMG_krabbyPos61), findViewById(R.id.game_IMG_krabbyPos62), findViewById(R.id.game_IMG_krabbyPos63), findViewById(R.id.game_IMG_krabbyPos64)}
        };


    }

    private void initViews() {
        // Setting click listeners for player positions
        main_BTN_left.setOnClickListener(view -> gameManager.updateSpongebobPlace(Direction.LEFT));
        main_BTN_right.setOnClickListener(view -> gameManager.updateSpongebobPlace(Direction.RIGHT));
        // Setting the background image
        main_IMG_background.setImageResource(R.drawable.filed_beckground);

        // Initializing the hearts visibility
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
                    case KRABBY_PATTY: {
                        game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.krabby_patty);
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
    public void scoreAdd(int score){
        game_LBL_score.setText(score+ ""); //set score text
    }
    @Override
    public void onGameLost() {

        Intent endGameIntent = new Intent(MainActivity.this, EndActivity.class);
        startActivity(endGameIntent);
        finish();
    }
}
