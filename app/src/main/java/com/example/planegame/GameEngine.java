package com.example.planegame;

import static android.os.SystemClock.sleep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private Game game;
    private final Context context;
    private final int cellSize;
    private final Paint paint;
    private final Plane plane;
    private final GameBoard gameBoard;
    private int[][] board;
    private final List<int[]> touchedCells;
    private boolean isPlaneMoving = false;
    private boolean isRunning;
    private List<int[]> path;
    private int pathIndex = 0;

    private Bitmap touchedCellBitmap;

    public GameEngine(Context context, int cellSize) {
        this.context = context;
        this.paint = new Paint();
        this.cellSize = cellSize;
        this.isRunning = true;
        this.plane = new Plane(context, 0, 0, cellSize);
        this.touchedCells = new ArrayList<>();
        this.touchedCellBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pink);
        this.touchedCellBitmap = Bitmap.createScaledBitmap(touchedCellBitmap, cellSize,cellSize, true);
        board = new int[Constans.ROWS][Constans.COLS];
        this.gameBoard = new GameBoard(context, board, cellSize);
    }

    public void draw(Canvas canvas) {
        if(canvas == null) {
            return;
        }
        //Рисуем поле
        gameBoard.draw(canvas, board);
        //Рисуем выделенные клетки
        for (int[] cell : touchedCells) {
            int row = cell[0];
            int col = cell[1];
            if(board[row][col] != 2) {
                double left = col * cellSize;
                double top = row * cellSize;
                canvas.drawBitmap(touchedCellBitmap, (float)left, (float)top, paint);
            }
        }
        plane.draw(canvas);
    }
    //Собираем маршрут для самолета
    public void handleTouchEvent(int row, int col) {
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
        path = new ArrayList<>(touchedCells);
        isPlaneMoving = true;
        pathIndex = 0;
        movePlane();
    }
    //Движение самолета
    public void movePlane() {
        isPlaneMoving = true;
        if(pathIndex < path.size()) {
            int[] nextCell = path.get(pathIndex);
            if(!isAdjecent(plane.getRow(), plane.getCol(), nextCell[0], nextCell[1])) {
                isPlaneMoving = false;
                touchedCells.clear();
                return;
            }
            plane.setPosition(nextCell[0], nextCell[1]);
            pathIndex++;
            sleep(65L);
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
        return board[plane.getRow()][plane.getCol()] == 1;
    }
    //Заканчиваем действия игры и переходим в меню
    public void endGame() {
        isRunning = false;
        isPlaneMoving = false;
        touchedCells.clear();
        if(context instanceof Activity) {
            Activity activity = (Activity) context;
            Intent intent = new Intent(context, GameLevels.class);
            context.startActivity(intent); activity.finish();
        }
        game.surfaceDestroyed(game.getHolder());
    }

    public void setGame(Game game) {
        this.game = game;
    }

    //Обновляем картинку
    public void update() {
        if(!isRunning) {
            return;
        }
        if(isPlaneMoving) {
            movePlane();
        }
        if(checkFinish()) {
            endGame();
        }
    }
}
