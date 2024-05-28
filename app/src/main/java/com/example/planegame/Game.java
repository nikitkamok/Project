package com.example.planegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private int[][] board;
    private int planeRow, planeCol;

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
        cellSize = Constans.SCREEN_HEIGHT / Constans.ROWS;
        //Создаем игровой процесс
        gameEngine = new GameEngine(getContext(), cellSize);
        gameEngine.setGame(this);
        if(board != null) {
            gameEngine.setBoard(board);
        }
        gameEngine.setPlanePosition(planeRow, planeCol);
        //Запускаем поток
        gameThread = new GameThread(holder, this);
        gameThread.setRunning(true);
        gameThread.start();
        //Время

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        //Чистим память и останавливаем поток
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

    //Считываем клетки до которых коснулись и делаем дела
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int col = (int) (event.getX() / cellSize);
        int row = (int) (event.getY() / cellSize);
        //Проводим маршрут
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            gameEngine.handleTouchEvent(row, col);
            invalidate();
        }
        //Запускаем самолет
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameEngine.handlePlaneTouchEvent(row, col)) {
                gameEngine.startPlaneMovement();
            }
        }
        return true;
    }
    //Делаем игровое поле
    public void setBoard(int[][] board) {
        this.board = board;
        if (gameEngine != null) {
            gameEngine.setBoard(board);
        }
    }
    //Устанавливаем позицию самолета
    public void setPlanePosition(int row, int col) {
        this.planeRow = row;
        this.planeCol = col;
        if (gameEngine != null) {
            gameEngine.setPlanePosition(row, col);
        }
    }

    public void endGame() {
        gameThread.setRunning(false);
        Intent intent = new Intent(getContext(), GameLevels.class);
        getContext().startActivity(intent);
        ((Activity)getContext()).finish();
    }

    public void update() {
        gameEngine.update();
    }
}
