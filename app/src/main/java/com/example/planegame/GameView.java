package com.example.planegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class GameView extends SurfaceView {
    private SurfaceHolder holder;
    private GameThread gameThread;
    private Bitmap bmWater1, bmWater2;
    public static int sizeOfMap = 75 * Constans.SCREEN_WIDTH / 1080;
    private ArrayList <Water> arrWater = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        gameThread = new GameThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                boolean retry = true;
                gameThread.setRunning(false);
                while(retry) {
                    try {
                        gameThread.join();
                        retry = false;
                    } catch(InterruptedException e) {
                    }
                }
            }
        });

        bmWater1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.water1);
        bmWater1 = Bitmap.createScaledBitmap(bmWater1, sizeOfMap, sizeOfMap, true);
        bmWater2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.water2);
        bmWater2 = Bitmap.createScaledBitmap(bmWater2, sizeOfMap, sizeOfMap, true);

        for(int i = 0; i < Constans.HEIGHT; ++i) {
            for(int j = 0; j < Constans.WIDTH; ++j) {
                if((i + j) % 2 == 0) {
                    arrWater.add(new Water(bmWater1, j * sizeOfMap + Constans.SCREEN_WIDTH / 2 - (Constans.WIDTH / 2) * sizeOfMap,
                            i * sizeOfMap + 100 * Constans.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                }
                else {
                    arrWater.add(new Water(bmWater2, j * sizeOfMap + Constans.SCREEN_WIDTH / 2 - (Constans.WIDTH / 2) * sizeOfMap,
                            i * sizeOfMap + 100 * Constans.SCREEN_HEIGHT / 1920, sizeOfMap, sizeOfMap));
                }
            }
        }
    }
    public void onDraw(Canvas canvas) {
        for(int i = 0; i < arrWater.size(); ++i) {
            canvas.drawBitmap(arrWater.get(i).getBitmap(), arrWater.get(i).getX(), arrWater.get(i).getY(), null);
        }
    }
}
