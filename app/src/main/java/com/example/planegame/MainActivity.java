package com.example.planegame;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow().getDecorView().getWindowInsetsController()).hide(
                android.view.WindowInsets.Type.navigationBars() //спрятать меню навигации
        );

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constans.SCREEN_WIDTH = dm.widthPixels;
        Constans.SCREEN_HEIGHT = dm.heightPixels;

        setContentView(R.layout.activity_main);
        //объявление объектов главного меню
        Button buttonAchievements = findViewById(R.id.main_buttonAchievements);
        Button buttonSettings = findViewById(R.id.main_buttonSettings);
        Button buttonStats = findViewById(R.id.main_buttonStats);

        //переход из главного меню в меню уровней
        findViewById(R.id.main_buttonStart).setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, GameLevels.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog settingsDialog = new SettingsDialog();
                settingsDialog.show(getSupportFragmentManager(), "1");
            }
        });

        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatsDialog statsDialog = new StatsDialog();
                statsDialog.show(getSupportFragmentManager(), "2");
            }
        });

        buttonAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AchievementsDialog achievementsDialog = new AchievementsDialog();
                achievementsDialog.show(getSupportFragmentManager(), "3");
            }
        });

        soundPlayer = SoundPlayer.getInstance();
        playMusic();
    }

    public void playMusic() {
        if(!soundPlayer.isPlaying()) {
            soundPlayer.playSound(this, R.raw.how_did_we_do);
            soundPlayer.setVolume(0.05f);
        }
    }


    //Системная кнопка назад
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            String message = getString(R.string.exit_by_using_back);
            backToast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    //Системная кнопка назад

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPlayer.stopSound();
    }
}