package com.example.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Coin {
    private final int cellSize;
    private int row, col;
    private final Paint paint;
    private Bitmap coinBitmap;

    public Coin(Context context, int initialRow, int initialCol, int cellSize) {
        this.row = initialRow;
        this.col = initialCol;
        this.cellSize = cellSize;
        this.paint = new Paint();
        this.coinBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin);
        this.coinBitmap = Bitmap.createScaledBitmap(coinBitmap, cellSize - 30, cellSize - 30, false);
    }

    public void draw(Canvas canvas) {
        int left = col * cellSize + 15;
        int top = row * cellSize + 15;
        canvas.drawBitmap(coinBitmap, left, top, paint);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
