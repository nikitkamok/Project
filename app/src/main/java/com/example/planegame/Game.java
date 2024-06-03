package com.example.planegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private GameEngine gameEngine;
    private int cellSize;
    private int[][] board;
    private int planeRow, planeCol;
    //Музыка
    private SoundPlayer soundPlayer;
    //Добавляем переменные для хранения фактора масштабирования и смещения
    private float scaleFactor = 1.0f;
    private float minScaleFactor = 1.0f;
    private float maxScaleFactor = 1.5f;
    private float focusX = 0;
    private float focusY = 0;
    private Matrix matrix = new Matrix();
    private ScaleGestureDetector scaleGestureDetector;
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

        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        cellSize = Math.max(Constans.SCREEN_WIDTH / Constans.COLS, Constans.SCREEN_HEIGHT / Constans.ROWS);
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

        //Запускаем музыку
        soundPlayer = SoundPlayer.getInstance();
        soundPlayer.playSound(this.getContext(), R.raw.crystal_clear);
        soundPlayer.setVolume(0.1f);
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
        canvas.save();
        canvas.setMatrix(matrix);
        gameEngine.draw(canvas);
        canvas.restore();
    }

    //Считываем клетки до которых коснулись и делаем дела
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            scaleGestureDetector.onTouchEvent(event);
        }
        else {
            float[] touchPoint = {event.getX(), event.getY()};
            Matrix inverse = new Matrix();
            matrix.invert(inverse);
            inverse.mapPoints(touchPoint);

            int col = (int) touchPoint[0] / cellSize;
            int row = (int) touchPoint[1] / cellSize;
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
        }
        return true;
    }

    //Класс слушателя для обработки жеста масштабирования
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(minScaleFactor, Math.min(scaleFactor, maxScaleFactor));
            focusX = detector.getFocusX();
            focusY = detector.getFocusY();
            matrix.reset();
            matrix.postScale(scaleFactor, scaleFactor, focusX, focusY);
            invalidate();
            return true;
        }
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
    //Завершаем игру
    public void endGame() {
        gameThread.setRunning(false);
        soundPlayer.stopSound();
        Intent intent = new Intent(getContext(), GameLevels.class);
        getContext().startActivity(intent);
        ((Activity)getContext()).finish();
    }

    public void update() {
        gameEngine.update();
    }
}