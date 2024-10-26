package com.example.spongebobvsjellyfish.Screen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;

import com.example.spongebobvsjellyfish.Interfaces.MoveCallback;
import com.example.spongebobvsjellyfish.Models.Player;
import com.example.spongebobvsjellyfish.Models.PlayerList;
import com.example.spongebobvsjellyfish.R;
import com.example.spongebobvsjellyfish.BoardUpdateListener;
import com.example.spongebobvsjellyfish.Logic.GameManager;
import com.example.spongebobvsjellyfish.Models.Direction;
import com.example.spongebobvsjellyfish.Models.SquareEntity;
import com.example.spongebobvsjellyfish.Utilities.MoveDetector;
import com.example.spongebobvsjellyfish.Utilities.SignalManager;
import com.example.spongebobvsjellyfish.Utilities.SoundPlayer;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
    private final int ROWS = 7;
    private final int COLS = 5;
    private SoundPlayer soundPlayer;
    private MaterialTextView game_LBL_score;
    private MoveDetector moveDetector;
    private boolean isFastMode;
    private boolean isSensorMode;
    private int score = 0;
    private SharedPreferences sp;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private static final int FINE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("PlayerPrefs", Context.MODE_PRIVATE);

        findViews();

        isFastMode = getIntent().getBooleanExtra("EXTRA_FAST_SPEED", false);
        isSensorMode = getIntent().getBooleanExtra("EXTRA_SENSOR_MOOD", false);
        String playerName = getIntent().getStringExtra("EXTRA_PLAYER_NAME");
        initViews();
        initGameManager(isFastMode, isSensorMode, playerName);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setCurrentLocation();

        SignalManager.init(this);
        initMoveDetector();
    }

    private void setCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                currentLocation = location;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCurrentLocation();
            }
        } else {
            Toast.makeText(this, "Location permission denied, please allow the permission", Toast.LENGTH_LONG).show();
        }
    }

    private void initMoveDetector() {
        moveDetector = new MoveDetector(this,
                new MoveCallback() {
                    @Override
                    public void moveRight() {

                        gameManager.updateSpongebobPlace(Direction.RIGHT);
                    }

                    @Override
                    public void moveLeft() {

                        gameManager.updateSpongebobPlace(Direction.LEFT);
                    }

                    @Override
                    public void moveUp() {
                        gameManager.updateSpongebobPlace(Direction.UP);
                    }

                    @Override
                    public void moveDown() {
                        gameManager.updateSpongebobPlace(Direction.DOWN);
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPlayer.stopSoundGame(R.raw.game);
        if (isSensorMode) {
            moveDetector.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        soundPlayer = new SoundPlayer(this);
        soundPlayer.playSoundGame(R.raw.game);
        if (isSensorMode) {
            moveDetector.start();
        }
    }

    private void initGameManager(boolean isFastMood, boolean isSensorMood, String playerName) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.jellyfish);
                game_IMG_JellyMatrix[i][j].setVisibility(View.INVISIBLE);
            }
        }
        gameManager = new GameManager(this, isFastMood, isSensorMood, playerName);
        gameManager.setListener(this);
    }

    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.list_BTN_location);

        main_IMG_hearts = new AppCompatImageView[]{
                findViewById(R.id.main_IMG_heart3),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart1)
        };
        game_LBL_score = findViewById(R.id.game_LBL_score);

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
        if (!isSensorMode) {
            main_BTN_left.setOnClickListener(view -> gameManager.updateSpongebobPlace(Direction.LEFT));
            main_BTN_right.setOnClickListener(view -> gameManager.updateSpongebobPlace(Direction.RIGHT));
        } else {
            main_BTN_left.setVisibility(View.INVISIBLE);
            main_BTN_right.setVisibility(View.INVISIBLE);
        }
        main_IMG_background.setImageResource(R.drawable.filed_beckground);
        for (AppCompatImageView heart : main_IMG_hearts) {
            heart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBoardUpdated(SquareEntity[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                switch (board[i][j]) {
                    case EMPTY:
                        game_IMG_JellyMatrix[i][j].setVisibility(View.INVISIBLE);
                        break;
                    case JELLY_FISH:
                        game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.jellyfish);
                        game_IMG_JellyMatrix[i][j].setVisibility(View.VISIBLE);
                        break;
                    case SPONGE_BOB:
                        game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.player);
                        game_IMG_JellyMatrix[i][j].setVisibility(View.VISIBLE);
                        break;
                    case KRABBY_PATTY:
                        game_IMG_JellyMatrix[i][j].setImageResource(R.drawable.krabby_patty);
                        game_IMG_JellyMatrix[i][j].setVisibility(View.VISIBLE);
                        break;
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
    public void scoreAdd(int score) {
        game_LBL_score.setText(score + "");
    }

    public void writeSharedPreferences(PlayerList playerList) {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear(); // Clear previous data
        for (int i = 0; i < playerList.getAllPlayers().size(); i++) {
            Player player = playerList.getAllPlayers().get(i);
            editor.putString("name_" + i, player.getName());
            editor.putInt("score_" + i, player.getScore());
            editor.putFloat("latitude_" + i, (float) player.getLatitude());   // Save latitude
            editor.putFloat("longitude_" + i, (float) player.getLongitude()); // Save longitude
        }
        editor.apply();
    }

    @Override
    public void onGameLost() {
        Player optionalPlayer = new Player();
        score = gameManager.getScore();
        optionalPlayer.setScore(score);
        String name = gameManager.getPlayername();
        optionalPlayer.setName(name);
        optionalPlayer.setLatitude(currentLocation.getLatitude());
        optionalPlayer.setLongitude(currentLocation.getLongitude());

        // Load existing players from SharedPreferences
        PlayerList playerList = new PlayerList();
        readSharedPreferences(playerList);

        // Add the new player and save back to SharedPreferences
        if (playerList.addPlayer(optionalPlayer)) {
            writeSharedPreferences(playerList);
        }

        Intent endGameIntent = new Intent(MainActivity.this, EndActivity.class);
        startActivity(endGameIntent);
        finish();
    }

    public void readSharedPreferences(PlayerList playerList) {
        for (int i = 0; ; i++) {
            String strName = sp.getString("name_" + i, null);
            int intScore = sp.getInt("score_" + i, -1);
            double latitude = sp.getFloat("latitude_" + i, 0);   // Latitude
            double longitude = sp.getFloat("longitude_" + i, 0); // Longitude
            if (strName != null && intScore != -1) {
                playerList.addPlayer(new Player().setName(strName).setScore(intScore).setLatitude(latitude).setLongitude(longitude));
            } else {
                break; // Exit the loop if no more players are found
            }
        }
    }
}
