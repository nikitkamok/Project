package com.example.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Barrier {
    private int cellSize;
    private int row, col;
    private Paint paint;
    private Bitmap barrierBitmap;

    public Barrier(Context context, int initialRow, int initialCol, int cellSize) {
        this.row = initialRow;
        this.col = initialCol;
        this.cellSize = cellSize;
        this.paint = new Paint();
        this.barrierBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cloud);
        this.barrierBitmap = Bitmap.createScaledBitmap(barrierBitmap, (int) ((float)cellSize * 1.2), (int) ((float)cellSize * 1.2), false);
    }

    public void draw(Canvas canvas) {
        int left = col * cellSize - (int) ((float)cellSize * 0.15);
        int top = row * cellSize - (int) ((float)cellSize * 0.15);
        canvas.drawBitmap(barrierBitmap, left, top, paint);
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
