package com.example.planegame;

import static android.os.SystemClock.sleep;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameEngine {
    private final int cellSize;
    private final Paint paint;
    private final Plane plane;
    private int planeRow = 5, planeCol = 5;
    private final List<int[]> touchedCells;
    private boolean isPlaneMoving = false;
    private List<int[]> path;
    private int pathIndex = 0;

    private Bitmap touchedCellBitmap;
    private Bitmap backgrounfdBitmap;

    public GameEngine(Context context, int cellSize) {
        this.paint = new Paint();
        this.plane = new Plane(context, planeRow, planeCol, cellSize);
        this.cellSize = cellSize;
        this.touchedCells = new ArrayList<>();
        this.touchedCellBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pink);
        this.touchedCellBitmap = Bitmap.createScaledBitmap(touchedCellBitmap, cellSize,cellSize, true);
        this.backgrounfdBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_water);
        this.backgrounfdBitmap = Bitmap.createScaledBitmap(backgrounfdBitmap, Constans.SCREEN_WIDTH, Constans.SCREEN_HEIGHT, true);
    }

    public void draw(Canvas canvas) {
        if(canvas == null) {
            return;
        }
        //Рисуем поле
        canvas.drawBitmap(backgrounfdBitmap, 0, 0, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        for (int row = 0; row < Constans.ROWS; row++) {
            for (int col = 0; col < Constans.COLS; col++) {
                int left = col * cellSize;
                int top = row * cellSize;
                int right = left + cellSize;
                int bottom = top + cellSize;
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }
        //Рисуем выделенные клетки
        for (int[] cell : touchedCells) {
            int row = cell[0];
            int col = cell[1];
            int left = col * cellSize;
            int top = row * cellSize;
            canvas.drawBitmap(touchedCellBitmap, left, top, paint);
        }
        //Рисуем самолет
        plane.draw(canvas);
    }
    //Собираем маршрут для самолета
    public void handleTouchEvent(int row, int col) {
        if(row >= 0 && row < Constans.ROWS && col >= 0 && col < Constans.COLS) {
            int[] cell = {row, col};
            if(!isCellValidate(row, col)) {
                touchedCells.add(cell);
            }
        }
    }
    //Проверяем можно ли зайти в клетку
    private boolean isCellValidate(int row, int col) {
        for(int[] cell : touchedCells) {
            if (cell[0] == row && cell[1] == col) {
                return true;
            }
        }
        return false;
    }
    //Тыркаем по самолету
    public boolean handlePlaneTouchEvent(int row, int col) {
        return row == plane.getRow() && col == plane.getCol() && !isPlaneMoving;
    }
    //Начинам движение самолета
    public void startPlaneMovement() {
        path = new ArrayList<>(touchedCells);
        isPlaneMoving = true;
        pathIndex = 0;
        movePlane();
    }
    public void movePlane() {
        isPlaneMoving = true;
        if(pathIndex < path.size()) {
            int[] nextCell = path.get(pathIndex);
            if(!isAdjecent(plane.getRow(), plane.getCol(), nextCell[0], nextCell[1])) {
                isPlaneMoving = false;
                touchedCells.clear();
                return;
            }
            plane.setPosition(nextCell[0], nextCell[1]);
            pathIndex++;
            sleep(65L);
        }
        else {
            isPlaneMoving = false;
            touchedCells.clear();
        }
    }
    //Проверяем можем ли идти в следующую ячейку
    private boolean isAdjecent(int row1, int col1, int row2, int col2) {
        int rowDiff = Math.abs(row1 - row2);
        int colDiff = Math.abs(col1 - col2);
        return !(rowDiff > 1 || colDiff > 1);
    }

    public void update() {
        if(isPlaneMoving) {
            movePlane();
        }
    }
}
