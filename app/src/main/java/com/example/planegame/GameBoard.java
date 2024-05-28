package com.example.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameBoard {
    private int[][] board;
    private final int cellSize;
    private final Paint paint;
    private final Barrier barrier;
    private final Coin coin;
    private Bitmap backgroundBitmap;
    private Bitmap finishBitmap;

    public GameBoard(Context context, int[][] board, int cellSize) {
        this.board = board;
        this.cellSize = cellSize;
        this.paint = new Paint();
        this.barrier = new Barrier(context, 0, 0, cellSize);
        this.coin = new Coin(context, 0, 0, cellSize);
        this.backgroundBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_water);
        this.backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, Constans.SCREEN_WIDTH, Constans.SCREEN_HEIGHT, true);
        this.finishBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.finish);
        this.finishBitmap = Bitmap.createScaledBitmap(finishBitmap, cellSize, cellSize, true);
    }

    public void updateBoard(int[][] board) {
        this.board = board;
    }

    public void draw(Canvas canvas, int[][] board) {
        if(canvas == null) {
            return;
        }
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        for (int row = 0; row < Constans.ROWS; ++row) {
            for (int col = 0; col < Constans.COLS; ++col) {
                int left = col * cellSize;
                int top = row * cellSize;
                int right = left + cellSize;
                int bottom = top + cellSize;
                //Рисуем на поле
                switch (board[row][col]) {
                    case 2: //Рисуем барьеры
                        barrier.setPosition(row, col);
                        barrier.draw(canvas);
                        break;
                    case 3: //Рисуем монеты
                        coin.setPosition(row, col);
                        coin.draw(canvas);
                        break;
                    case 4: //Рисуем финиш
                        canvas.drawBitmap(finishBitmap, left, top, paint);
                        break;
                    default: //Рисуем клеточки
                        canvas.drawRect(left, top, right, bottom, paint);
                        break;
                }
            }
        }
    }
}
