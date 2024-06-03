package com.example.planegame;

import android.os.Bundle;

public class Level5 extends BaseLevel {
    private Game gameView;
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.universal);
        gameView = findViewById(R.id.game);
        timer = findViewById(R.id.timer);
        soundPlayer = SoundPlayer.getInstance();

        if (gameView != null) {
            int[][] board = new int[][]{
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {2, 2, 3, 2, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 3, 2, 2},
                    {2, 2, 0, 2, 0, 2, 0, 0, 2, 0, 2, 0, 2, 0, 2, 3, 2, 0, 2, 0, 2, 2},
                    {2, 2, 0, 0, 0, 2, 0, 0, 2, 0, 2, 0, 2, 0, 2, 2, 2, 0, 2, 0, 2, 2},
                    {2, 2, 2, 2, 0, 2, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 2, 2},
                    {2, 2, 0, 0, 0, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 0, 2, 2},
                    {2, 2, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 2, 0, 2, 2},
                    {2, 2, 0, 2, 3, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 2, 2},
                    {2, 2, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2},
                    {2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 2, 0, 0, 0, 0, 0, 4, 2, 2},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            };
            gameView.setBoard(board);

            // Устанавливаем начальную позицию самолета
            gameView.setPlanePosition(1, 9);

            //Запускаем таймер
            startTimer();
            //Запускаем музыку
            soundPlayer.playSound(this, R.raw.crystal_clear);
            soundPlayer.setVolume(0.1f);
        }

        //Выйти
        findViewById(R.id.levels_backToMenuBtn).setOnClickListener(v -> {
            stopTimer();
            soundPlayer.stopSound();
            gameView.endGame();
        });
    }
}