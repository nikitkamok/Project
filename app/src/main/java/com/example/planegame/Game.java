package com.example.planegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private GameEngine gameEngine;
    private int cellSize;

    public Game(Context context) {
        super(context);
        init();
    }

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Game(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        cellSize = Math.min(Constans.SCREEN_WIDTH / Constans.COLS, Constans.SCREEN_HEIGHT / Constans.ROWS);

        gameEngine = new GameEngine(getContext(), cellSize);
        gameThread = new GameThread(holder, this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void drawGameBoard(Canvas canvas) {
        gameEngine.draw(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int col = (int) (event.getX() / cellSize);
        int row = (int) (event.getY() / cellSize);

        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            gameEngine.handleTouchEvent(row, col);
            invalidate();
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameEngine.handlePlaneTouchEvent(row, col)) {
                gameEngine.startPlaneMovement();
            }
        }
        return true;
    }

    public void update() {
        gameEngine.update();
    }
}
