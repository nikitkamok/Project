package com.example.planegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Level1 extends AppCompatActivity {

    private static boolean isPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getWindowInsetsController().hide(
                android.view.WindowInsets.Type.navigationBars() //спрятать меню навигации
        );
        LayoutInflater factory = LayoutInflater.from(this);
        View gameBoard = factory.inflate(R.layout.universal, null);
        setContentView(R.layout.universal);
        //объявление объектов уровня
        Button buttonPauseMenu = (Button) findViewById(R.id.levels_pauseMenuBtn);
        Button buttonReset = (Button) findViewById(R.id.levels_resetLevelBtn);
        Button buttonSettings = (Button) findViewById(R.id.levels_settingsBtn);
        Button buttonResume = (Button) findViewById(R.id.levels_resumePauseBtn);
        Button buttonExit = (Button) findViewById(R.id.levels_exitBtn);
        LinearLayout pauseMenu = (LinearLayout) findViewById(R.id.levels_container3);

        //поставить игру на паузу
        buttonPauseMenu.setOnClickListener(v -> {
            try {
                //отстановить игру
                pauseMenu.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }
        });
        //начать уровень с начала
        buttonReset.setOnClickListener(v -> {
            try {
                pauseMenu.setVisibility(View.INVISIBLE);
                //ну там какой-то код для возобновления уровня
            } catch (Exception e) {
            }
        });
        //открыть меню настроек
        buttonSettings.setOnClickListener(v -> {
            try {
                pauseMenu.setVisibility(View.INVISIBLE);
                //ну там открыть менюшку настроек
            } catch (Exception e) {
            }
        });
        //возобновить игру
        buttonResume.setOnClickListener(v -> {
            try {
                pauseMenu.setVisibility(View.INVISIBLE);
            } catch (Exception e) {
            }
        });
        //выйти в выбор уровней
        buttonExit.setOnClickListener(v -> {
            try {
                pauseMenu.setVisibility(View.INVISIBLE);
                //закончить работу игры
                Intent intent = new Intent(Level1.this, GameLevels.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });
    }
}