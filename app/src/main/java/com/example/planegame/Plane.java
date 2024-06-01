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
        this.planeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ufo);
        this.planeBitmap = Bitmap.createScaledBitmap(planeBitmap, (int) ((float)cellSize * 1.2), (int) ((float)cellSize * 1.2), false);
    }

    public void draw(Canvas canvas) {
        int left = col * cellSize - (int) ((float)cellSize * 0.1);
        int top = row * cellSize - (int) ((float)cellSize * 0.1);
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
}
