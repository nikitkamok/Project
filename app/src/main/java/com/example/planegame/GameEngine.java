package com.example.planegame;

import static android.os.SystemClock.sleep;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private Game game;
    private final Context context;
    private final int cellSize;
    private final Paint paint;
    //Самолетик
    private final Plane plane;
    private boolean isPlaneMoving = false;
    //Поле
    private final GameBoard gameBoard;
    private int[][] board;
    //Монеты
    private int collectedCoins = 0;
    //Маршрут
    private final List<int[]> touchedCells;
    private Bitmap touchedCellBitmap;
    //Обработка маршрута
    private List<int[]> path;
    private int pathIndex = 0;
    //Звуки
    private SoundPool sounds;
    private int gotCoin;
    private int movePlane;
    private int finish;

    public GameEngine(Context context, int cellSize) {
        this.context = context;
        this.paint = new Paint();
        this.cellSize = cellSize;
        this.plane = new Plane(context, 0, 0, cellSize);
        this.touchedCells = new ArrayList<>();
        this.touchedCellBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pink);
        this.touchedCellBitmap = Bitmap.createScaledBitmap(touchedCellBitmap, cellSize,cellSize, false);
        board = new int[Constans.ROWS][Constans.COLS];
        this.gameBoard = new GameBoard(context, board, cellSize);
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        this.gotCoin = sounds.load(context, R.raw.gotcoin, 1);
        this.movePlane = sounds.load(context, R.raw.ufo, 1);
        this.finish = sounds.load(context, R.raw.victorious, 1);
    }

    public void draw(Canvas canvas) {
        canvas.save();
        //Рисуем поле
        gameBoard.draw(canvas, board);
        //Рисуем выделенные клетки
        for (int[] cell : touchedCells) {
            int row = cell[0];
            int col = cell[1];
            if(board[row][col] != 2) {
                int left = col * cellSize;
                int top = row * cellSize;
                canvas.drawBitmap(touchedCellBitmap, left, top, paint);
            }
        }
        plane.draw(canvas);

        canvas.restore();
    }
    //Собираем маршрут для самолета
    public void handleTouchEvent(int row, int col) {
        //Проверяем что мы находимся в поле
        if(row >= 0 && row < Constans.ROWS && col >= 0 && col < Constans.COLS) {
            int[] cell = {row, col};
            if(isCellValidate(row, col)) {
                if (!touchedCells.isEmpty()) {
                    int[] lastCell = touchedCells.get(touchedCells.size() - 1);
                    if (board[lastCell[0]][lastCell[1]] == 1) {
                        return; //Если предыдущая ячейка финишная, не добавляем новую ячейку
                    }
                }
                touchedCells.add(cell);
            }
        }
    }
    //Проверяем можно ли зайти в клетку
    private boolean isCellValidate(int row, int col) {
        for(int[] cell : touchedCells) {
            if (cell[0] == row && cell[1] == col || board[row][col] == 2) {
                return false;
            }
        }
        return true;
    }
    //Тыркаем по самолету
    public boolean handlePlaneTouchEvent(int row, int col) {
        return row == plane.getRow() && col == plane.getCol() && !isPlaneMoving;
    }
    //Начинам движение самолета
    public void startPlaneMovement() {
        path = touchedCells;
        pathIndex = 0;
        isPlaneMoving = true;
        movePlane();
    }

    public void movePlane() {
        isPlaneMoving = true;
        if(pathIndex < path.size()) {
            int[] nextCell = path.get(pathIndex);
            if(!isAdjecent(plane.getRow(), plane.getCol(), nextCell[0], nextCell[1])) {
                isPlaneMoving = false;
                touchedCells.clear();
                return;
            }
            if(pathIndex > 1) {
                sounds.play(movePlane, 0.3f, 0.3f, 0, 0, 1.5f);
            }
            plane.setPosition(nextCell[0], nextCell[1]);
            pathIndex++;
            sleep(75L);
        }
        else {
            isPlaneMoving = false;
            touchedCells.clear();
        }
    }
    //Проверяем можем ли идти в следующую ячейку
    private boolean isAdjecent(int row1, int col1, int row2, int col2) {
        int rowDiff = Math.abs(row1 - row2);
        int colDiff = Math.abs(col1 - col2);
        //Проверяем чтобы маршрут начинался с самолета
        if(pathIndex == 0) {
            return rowDiff == 0 && colDiff == 0;
        }
        //Проверяем можем ли мы идти дальше по маршруту
        else {
            return rowDiff == 1 && colDiff == 0 || rowDiff == 0 && colDiff == 1;
        }
    }

    //Проверяем собрали ли мы монету
    private void checkCoinCollection() {
        int row = plane.getRow();
        int col = plane.getCol();
        if(board[row][col] == 3) {
            collectedCoins++;
            board[row][col] = 0;
            if(context instanceof Activity) {
                ((Activity) context).runOnUiThread(() -> {
                    TextView levels_coinCount = ((Activity) context).findViewById(R.id.coin_count);
                    levels_coinCount.setText(String.valueOf(collectedCoins));
                });
            }
            sounds.play(gotCoin, 0.1f, 0.1f, 0, 0, 1.5f);
        }

    }

    public void setGame(Game game) {
        this.game = game;
    }

    //Раставляем объекты
    public void setBoard(int[][] board) {
        this.board = board;
        this.gameBoard.updateBoard(board);
    }

    //Устанавливаем позицию самолета
    public void setPlanePosition(int row, int col) {
        plane.setPosition(row, col);
    }

    //Проверяем достигли ли мы финиша
    private boolean checkFinish() {
        return board[plane.getRow()][plane.getCol()] == 4;
    }

    //Обновляем картинку
    public void update() {
        if(checkFinish()) {
            sounds.play(finish, 0.1f, 0.1f, 0, 0, 1.5f);
            isPlaneMoving = false;
            touchedCells.clear();
            if(!path.isEmpty()) {
                path.clear();
            }
            game.endGame();
        }
        if(isPlaneMoving) {
            movePlane();
        }
        checkCoinCollection();
    }
}