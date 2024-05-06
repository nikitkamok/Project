package com.example.planegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
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
        Button buttonBack = (Button) findViewById(R.id.gamelevels_buttonBack);
        TextView level1 = (TextView) findViewById(R.id.gamelevels_level1);

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
