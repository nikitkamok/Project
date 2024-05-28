package com.example.planegame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private final Game game;
    private final SurfaceHolder surfaceHolder;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, Game game) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
        running = false;
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
                synchronized (surfaceHolder) {
                    game.update();
                    if(canvas != null) {
                        game.drawGameBoard(canvas);
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
