package com.example.planegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private GameEngine gameEngine;
    private int cellSize;

    public Game(Context context) {
        super(context);
        init(context);
    }

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Game(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        int width = getWidth();
        int height = getHeight();
        cellSize = Math.min(width / 14, height / 8);

        gameEngine = new GameEngine(getContext(), cellSize);
        gameThread = new GameThread(holder, this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
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
