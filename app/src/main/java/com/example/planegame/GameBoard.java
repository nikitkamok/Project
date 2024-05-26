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
    private Bitmap backgroundBitmap;
    private Bitmap finishBitmap;

    public GameBoard(Context context, int[][] board, int cellSize) {
        this.board = board;
        this.cellSize = cellSize;
        this.paint = new Paint();
        this.barrier = new Barrier(context, 0, 0, cellSize);
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
                canvas.drawRect(left, top, right, bottom, paint);

                if(board[row][col] == 1) {
                    canvas.drawBitmap(finishBitmap, left, top, paint);
                }

                if(board[row][col] == 2) {
                    barrier.setPosition(row, col);
                    barrier.draw(canvas);
                }
            }
        }
    }
}
