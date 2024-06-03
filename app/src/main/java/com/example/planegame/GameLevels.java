package com.example.planegame;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GameLevels extends AppCompatActivity {
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow().getDecorView().getWindowInsetsController()).hide(
                android.view.WindowInsets.Type.navigationBars() //спрятать меню навигации
        );
        setContentView(R.layout.gamelevels);

        soundPlayer = SoundPlayer.getInstance();

        //переход из меню уровней в главное меню
        findViewById(R.id.gamelevels_buttonBack).setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, MainActivity.class);
                startActivity(intent);finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //переход к первому уровню
        findViewById(R.id.gamelevels_level1).setOnClickListener(v -> {
            try {
                soundPlayer.stopSound();
                Intent intent = new Intent(GameLevels.this, Level1.class);
                startActivity(intent);finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //переход ко второму уровню
        findViewById(R.id.gamelevels_level2).setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level2.class);
                startActivity(intent);finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //переход к третьему уровню
        findViewById(R.id.gamelevels_level3).setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level3.class);
                startActivity(intent);finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //переход к четвертому уровню
        findViewById(R.id.gamelevels_level4).setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level4.class);
                startActivity(intent);finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //переход к пятому уровню
        findViewById(R.id.gamelevels_level5).setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level5.class);
                startActivity(intent);finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    //Системная кнопка назад
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(GameLevels.this, MainActivity.class);
            startActivity(intent);finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Системная кнопка назад

    @Override
    protected void onResume() {
        super.onResume();
        if (!soundPlayer.isPlaying()) {
            soundPlayer.playSound(this, R.raw.how_did_we_do);
            soundPlayer.setVolume(0.05f);
        }
    }
}
