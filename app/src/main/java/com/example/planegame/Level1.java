package com.example.planegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class Level1 extends AppCompatActivity {

    private static boolean isPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getWindowInsetsController().hide(
                android.view.WindowInsets.Type.navigationBars() //спрятать меню навигации
        );
        setContentView(R.layout.universal);
        //объявление объектов уровня
        Button buttonPauseMenu = (Button) findViewById(R.id.levels_pauseMenu);
        Button buttonReset = (Button) findViewById(R.id.levels_resetlevel);
        Button buttonSettings = (Button) findViewById(R.id.levels_settings);
        Button buttonResume = (Button) findViewById(R.id.levels_resumePause);
        Button buttonExit = (Button) findViewById(R.id.levels_exit);
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