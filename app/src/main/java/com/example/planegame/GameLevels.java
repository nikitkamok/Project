package com.example.planegame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameLevels extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getWindowInsetsController().hide(
                android.view.WindowInsets.Type.navigationBars() //спрятать меню навигации
        );
        setContentView(R.layout.gamelevels);
        //объявление объектов меню уровней
        Button buttonBack = findViewById(R.id.gamelevels_buttonBack);
        TextView level1 = findViewById(R.id.gamelevels_level1);
        TextView level2 = findViewById(R.id.gamelevels_level2);
        TextView level3 = findViewById(R.id.gamelevels_level3);
        TextView level4 = findViewById(R.id.gamelevels_level4);
        TextView level5 = findViewById(R.id.gamelevels_level5);

        //переход из меню уровней в главное меню
        buttonBack.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, MainActivity.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });
        //переход к первому уровню
        level1.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level1.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });
        //переход ко второму уровню
        level2.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level2.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });
        //переход к третьему уровню
        level3.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level3.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });
        //переход к четвертому уровню
        level4.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level4.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });
        //переход к пятому уровню
        level5.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(GameLevels.this, Level5.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });


    }
    //Системная кнопка назад

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            Intent intent = new Intent(GameLevels.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
        }
    }

    //Системная кнопка назад

}
