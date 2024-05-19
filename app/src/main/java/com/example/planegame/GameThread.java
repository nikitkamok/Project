package com.example.planegame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private GameBoard gameBoard;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;

    public GameThread(SurfaceHolder surfaceHolder, GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas();
                if(canvas != null) {
                    synchronized (surfaceHolder) {
                        gameBoard.drawGameBoard(canvas);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
