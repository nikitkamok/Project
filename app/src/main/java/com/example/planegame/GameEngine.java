package com.example.planegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameEngine {
    private final int cellSize;
    private final Paint paint;
    private final Plane plane;
    private final Set<String> touchedCells;
    private boolean isPlaneMoving = false;
    private List<int[]> path;
    private int pathIndex = 0;

    public GameEngine(Context context, int cellSize) {
        this.paint = new Paint();
        this.plane = new Plane(context, 0, 0, cellSize);
        this.cellSize = cellSize;
        this.touchedCells = new HashSet<>();
    }

    public void draw(Canvas canvas) {
        if(canvas == null) {
            return;
        }
        //Рисуем поле
        canvas.drawColor(Color.WHITE);
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
        plane.draw(canvas);
    }
    //Собираем маршрут для самолета
    public void handleTouchEvent(int row, int col) {
        String cell = row + "," + col;
        touchedCells.add(cell);
    }
    //Тыркаем по самолету
    public boolean handlePlaneTouchEvent(int row, int col) {
        return row == plane.getRow() && col == plane.getCol() && !isPlaneMoving;
    }
    //Начинам движение самолета
    public void startPlaneMovement() {
        path = new ArrayList<>();
        for (String cell : touchedCells) {
            String[] parts = cell.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            path.add(new int[]{row, col});
        }
        //Проверяем можем ли мы начинать движение
        if (isPathContinuous() && startsFromPlane()) {
            isPlaneMoving = true;
            pathIndex = 0;
            movePlane();
        } else {
            touchedCells.clear();
        }
    }
    //Проверяем непрерывность маршрута
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
    //Проверяем начинается ли маршрут от самолета
    private boolean startsFromPlane() {
        if (path.isEmpty()) {
            return false;
        }
        //Считываем позицию самолета и сравниваем с началом маршрута
        int[] firstCell = path.get(0);
        return firstCell[0] == plane.getRow() && firstCell[1] == plane.getCol();
    }

    public void movePlane() {
        if(pathIndex < path.size()) {
            plane.movePlane(path, pathIndex);
            pathIndex++;
        }
        else {
            isPlaneMoving = false;
            touchedCells.clear();
            //Устанавливаем последние местоположение самолета
            int[] lastCell = path.get(path.size() - 1);
            plane.setPosition(lastCell[0], lastCell[1]);
        }
    }
    public void update() {
        if(isPlaneMoving) {
            movePlane();
        }
    }
}
