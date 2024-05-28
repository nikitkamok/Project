package com.example.planegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Objects;

public class BaseLevel extends AppCompatActivity {

    protected TextView timer;
    protected long startTime;
    protected Handler timerHandler = new Handler();
    protected Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timer.setText(String.format("%d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getWindow().getDecorView().getWindowInsetsController()).hide(
                android.view.WindowInsets.Type.navigationBars()
        );

    }

    protected void startTimer() {
        startTime = System.currentTimeMillis();
        timerHandler.postDelayed(timerRunnable, 0);
    }

    protected void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
