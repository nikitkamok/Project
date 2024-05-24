package com.example.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Plane {
    private int rows = 8, cols = 14;
    private int row, col;
    private int cellSize;
    private Paint paint;
    private Bitmap planeBitmap;

    public Plane(Context context, int initialRow, int initialCol, int cellSize) {
        this.row = initialRow;
        this.col = initialCol;
        this.cellSize = cellSize;
        this.paint = new Paint();
        this.planeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);
        this.planeBitmap = Bitmap.createScaledBitmap(planeBitmap, cellSize, cellSize, false);
    }

    public void draw(Canvas canvas) {
        int left = col * cellSize;
        int top = row * cellSize;
        canvas.drawBitmap(planeBitmap, left, top, paint);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveUp() {
        //Проверяем не достигли ли мы границ поля
        if (row > 0) {
            row--;
        }
    }

    public void moveDown() {
        //Проверяем не достигли ли мы границ поля
        if (row < (rows - 1)) {
            row++;
        }
    }

    public void moveLeft() {
        //Проверяем не достигли ли мы границ поля
        if (col > 0) {
            col--;
        }
    }

    public void moveRight() {
        //Проверяем не достигли ли мы границ поля
        if (col < (cols - 1)) {
            col++;
        }
    }
}
