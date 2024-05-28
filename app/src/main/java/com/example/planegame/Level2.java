package com.example.planegame;

import android.os.Bundle;
import android.widget.Button;

public class Level2 extends BaseLevel {

    private Game gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.universal);
        gameView = findViewById(R.id.game);
        timer = findViewById(R.id.timer);

        if (gameView != null) {
            int[][] board = new int[][]{
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2},
                    {2, 0, 0, 0, 2, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2},
                    {2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2},
                    {2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 3, 0, 0, 0, 0, 4, 0, 0, 0, 2, 2},
                    {2, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2},
                    {2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2},
                    {2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2},
                    {2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2}
            };
            gameView.setBoard(board);

            // Устанавливаем начальную позицию самолета
            gameView.setPlanePosition(2, 2);

            startTimer();
        }

        Button buttonBackToMenu = findViewById(R.id.levels_backToMenuBtn);

        //Выйти
        buttonBackToMenu.setOnClickListener(v -> {
            stopTimer();
            gameView.endGame();
        });
    }
}