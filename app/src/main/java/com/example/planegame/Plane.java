package com.example.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.List;

public class Plane {
    private int cellSize;
    private int row, col;
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
        if (row < (Constans.ROWS - 1)) {
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
        if (col < (Constans.COLS - 1)) {
            col++;
        }
    }

    public void movePlane(List<int[]> path, int pathIndex) {
        if(pathIndex < path.size()) {
            int[] nextCell = path.get(pathIndex);
            int nextRow = nextCell[0];
            int nextCol = nextCell[1];
            //Определяем движение самолета
            int rowDiff = nextRow - getRow();
            int colDiff = nextCol - getCol();
            //Перемещаемся в соответсвии с направлением выше
            if (rowDiff < 0) {
                moveUp();
            } else if (rowDiff > 0) {
                moveDown();
            } else if (colDiff < 0) {
                moveLeft();
            } else if (colDiff > 0) {
                moveRight();
            }
        }
    }
}
