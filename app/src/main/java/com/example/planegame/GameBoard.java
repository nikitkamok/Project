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

public class GameBoard extends SurfaceView implements SurfaceHolder.Callback {
    private int rows = 8;
    private int cols = 14;
    private int cellSize;
    private Paint paint;
    private GameThread gameThread;

    private Set<String> touchedCells;
    private boolean isPlaneMoving = false;
    private int planeRow = 0, planeCol = 0;
    private List<int[]> path;
    private int pathIndex = 0;

    public GameBoard(Context context) {
        super(context);
        init();
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);

        GameBoard.this.getHolder().addCallback(this);

        touchedCells = new HashSet<>();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
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
        if (canvas == null) return;

        int width = getWidth();
        int height = getHeight();
        //Изменяем размеры ячеек под экран пользователя
        cellSize = Math.min(width / cols, height / rows);

        canvas.drawColor(Color.WHITE);

        //Рисуем поле
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col <= cols; ++col) {
                int left = col * cellSize;
                int top = row * cellSize;
                int right = left + cellSize;
                int bottom = top + cellSize;
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }

        //Выделяем выделиные ячейки
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        for (String cell : touchedCells) {
            String[] parts = cell.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            int left = col * cellSize;
            int top = row * cellSize;
            int right = left + cellSize;
            int bottom = top + cellSize;
            canvas.drawRect(left, top, right, bottom, paint);
        }

        //Рисуем самолет
        drawPlane(canvas, planeRow, planeCol);
    }

    private void drawPlane(Canvas canvas, int row, int col) {
        paint.setColor(Color.RED);
        int left = col * cellSize;
        int top = row * cellSize;
        int right = left + cellSize;
        int bottom = top + cellSize;
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
            int col = (int) (event.getX() / cellSize);
            int row = (int) (event.getY() / cellSize);

            if (row >= 0 && row < rows && col >= 0 && col < cols) {
                String cell = row + "," + col;
                if(!touchedCells.contains(cell)) {
                    touchedCells.add(cell);
                    invalidate();
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int col = (int) (event.getX() / cellSize);
            int row = (int) (event.getY() / cellSize);

            if (row == planeRow && col == planeCol && !isPlaneMoving) {
                startPlaneMovement();
            }
        }

        return true;
    }

    private void startPlaneMovement() {
        path = new ArrayList<>();
        for(String cell : touchedCells) {
            String[] parts = cell.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            path.add(new int[]{row, col});
        }

        if (isPathContinuous() && startsFromPlane()) {
            isPlaneMoving = true;
            pathIndex = 0;
            movePlane();
        }
        else {
            touchedCells.clear(); //Отчистка недопустимого пути
            invalidate();
        }
    }
    //Метод для проверки непрерывности пути
    private boolean isPathContinuous() {
        for (int i = 1; i < path.size(); i++) {
            int[] prev = path.get(i - 1);
            int[] curr = path.get(i);
            if (Math.abs(prev[0] - curr[0]) + Math.abs(prev[1] - curr[1]) > 1) {
                return false;
            }
        }
        return true;
    }
    //Метод для проверки, начинается ли путь с текущего положения самолета
    private boolean startsFromPlane() {
        if (path.isEmpty()) {
            return false;
        }
        int[] firstCell = path.get(0);
        return firstCell[0] == planeRow && firstCell[1] == planeCol;
    }

    private void movePlane() {
        if (pathIndex < path.size()) {
            planeRow = path.get(pathIndex)[0];
            planeCol = path.get(pathIndex)[1];
            pathIndex++;
            invalidate();
            postDelayed(this::movePlane, 500);
        }
        else {
            isPlaneMoving = false;
            touchedCells.clear(); //Отчистка недопустимого пути
            invalidate();
        }
    }
}
